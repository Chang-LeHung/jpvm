package org.jpvm.pvm;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.jpvm.stl.threading.PyThreadObject;
import org.yaml.snakeyaml.Yaml;

public class PVM {

  /** thread state of each thread */
  public static ThreadLocal<ThreadState> tss = new ThreadLocal<>();
  /** initialize here for compatibility */
  public static InterpreterState interpreterState = new InterpreterState(500);

  // prepare object system
  static {
    // init type and object
    PyObject.initBaseObject();
  }

  /** filename of the py file to be executed. */
  private final String filename;
  private final PyDictObject builtins;
  private PVM_STATE state;
  /** code of the py file to be executed. */
  private PyCodeObject code;
  /** global and local variables of the py file to be executed. */
  private PyDictObject globals;
  private PyDictObject locals;
  private PyModuleObject rootModule;
  private PyFrameObject rootFrame;
  private EvaluationLoop loop;

  public PVM(String filename) throws PyException, IOException {
    this.filename = filename;
    state = PVM_STATE.UNINITIALIZED;
    loadCode();
    // init thread and vm state
    init();
    // init built-in functions
    BuiltIn.doInit();
    // acquire builtins
    builtins = BuiltIn.dict;
    /*
     * in main module locals and globals are the same
     */
    initVirtualMachine();
  }

  public static ThreadState getThreadState() {
    ThreadState res = tss.get();
    if (res != null) return res;
    res = new ThreadState();
    res.setIs(interpreterState);
    tss.set(res);
    return res;
  }

  public static PVM create(String filename) throws PyException, IOException {
    return new PVM(filename);
  }

  public static void addModule(PyUnicodeObject name, PyModuleObject module) {
    getThreadState().getIs().addModule(name, module);
  }

  /** load bytecode of the py file to be executed. */
  private void loadCode() throws PyException, IOException {
    PycReader reader = new PycReader(filename);
    reader.doParse();
    code = reader.getCodeObject();
  }

  private void init() {
    if (tss == null) tss = new ThreadLocal<>();
    if (interpreterState == null) interpreterState = new InterpreterState(500);
  }

  private void initVirtualMachine() throws PyNotImplemented {
    rootModule = new PyModuleObject((PyUnicodeObject) code.getCoName());
    globals = rootModule.getDict();
    locals = rootModule.getDict();

    // ensure that the main module is named "__main__"
    rootModule.putAttribute(
        PyUnicodeObject.getOrCreateFromInternStringPool("__name__", true),
        PyUnicodeObject.getOrCreateFromInternStringPool("__main__", true));

    // register the Interpreter state
    registerInterpreterState();

    // register the module
    PVM.getThreadState()
        .getIs()
        .addModule(PyUnicodeObject.getOrCreateFromInternStringPool("__main__", true), rootModule);
    // add stl path to search path all stl are under org/jpvm/stl
    PVM.getThreadState()
        .getIs()
        .addSearchPath(PyUnicodeObject.getOrCreateFromInternStringPool("org/jpvm/stl", true));
    PVM.getThreadState()
        .getIs()
        .addSearchPath(PyUnicodeObject.getOrCreateFromInternStringPool("org/jpvmExt", true));

    Path path = Paths.get(filename);
    String base = path.toAbsolutePath().getParent().toString();
    code.setParentDir(path.toAbsolutePath().getParent().getParent().toString());
    PVM.getThreadState().getIs().addSearchPath(new PyUnicodeObject(base + "/__pycache__"));

    // set CodeObject filename
    Path basedir = path.toAbsolutePath().getParent().getParent();
    String[] split = filename.split("/");
    String name = split[split.length - 1].split("\\.")[0];
    String absFilePath = basedir.resolve(name + ".py").toString();
    this.code.updateFileName(absFilePath);
    state = PVM_STATE.INIT;
  }

  public PyModuleObject getRootModule() {
    return rootModule;
  }

  private void registerInterpreterState() {
    interpreterState.setBuiltins(builtins);
    Yaml yaml = new Yaml();
    var map = yaml.loadAs(this.getClass().getResourceAsStream("/jpvm-config.yml"), Map.class);
    Object o = map.get("vm-interval");
    if (o instanceof Integer) {
      interpreterState.setGILInterval((Integer) o);
    }
    o = map.get("max-recursive-depth");
    if (o instanceof Integer) {
      interpreterState.setMaxRecursionDepth((Integer) o);
    }
  }

  public String getFilename() {
    return filename;
  }

  public void run() throws PyException {
    state = PVM_STATE.RUNNING;
    rootFrame = new PyFrameObject(code, builtins, globals, locals);
    ThreadState ts = PVM.getThreadState();
    ts.setMainThread(true);
    ts.setCurrentFrame(rootFrame);
    loop = new EvaluationLoop(rootFrame);
    ts.getIs().takeGIL();
    PyObject object = loop.pyEvalFrame();
    if (object == null) PyErrorUtils.printExceptionInformation();
    ts.getIs().dropGIL();
    PyThreadObject.type.tss.remove();
    state = PVM_STATE.FINISHED;
  }

  public void exit() {
    tss.remove();
    tss = null;
    interpreterState = null; // help gc
    state = PVM_STATE.EXIT;
  }

  public void close() {
    exit();
  }

  public PyCodeObject getCode() {
    return code;
  }

  public PyDictObject getGlobals() {
    return globals;
  }

  private boolean isFinished() {
    return state == PVM_STATE.FINISHED;
  }

  private void ensureFinished() throws PyException {
    if (!isFinished()) {
      throw new PyException("VM not finished, please call run() first");
    }
  }

  public PyObject call(PyUnicodeObject name, PyTupleObject args, PyDictObject kwargs)
      throws PyException {
    ensureFinished();
    PyObject func = rootModule.getAttr(name);
    if (func instanceof PyFunctionObject f) {
      return Abstract.abstractCall(f, null, args, kwargs);
    }
    throw new PyException("function" + name + " not found");
  }

  public PyObject call(String name, PyTupleObject args, PyDictObject kwargs) throws PyException {
    return call(new PyUnicodeObject(name), args, kwargs);
  }

  public PyObject call(String name, PyObject... args) throws PyException {
    return call(name, new PyTupleObject(args), null);
  }

  public PyObject call(String name, Object... rawArgs) throws PyException {
    var args = (PyTupleObject) transformToPyObject(rawArgs);
    return call(name, args, null);
  }

  public PyObject transformToPyObject(Object o) throws PyException {
    return Utils.transformToPyObject(o);
  }

  public Object transformFromPyObject(PyObject o) throws PyException {
    return Utils.transformFromPyObject(o);
  }

  public PyObject getVariable(PyUnicodeObject name) throws PyException {
    ensureFinished();
    PyObject attr = rootModule.getAttr(name);
    if (attr == null) {
      throw new PyException("variable " + name + " not found");
    }
    return attr;
  }

  public PyDictObject getLocals() {
    return locals;
  }

  public PyDictObject getBuiltins() {
    return builtins;
  }

  public PyFrameObject getRootFrame() {
    return rootFrame;
  }

  public EvaluationLoop getLoop() {
    return loop;
  }

  enum PVM_STATE {
    UNINITIALIZED,
    INIT,
    RUNNING,
    FINISHED,
    EXIT
  }
}
