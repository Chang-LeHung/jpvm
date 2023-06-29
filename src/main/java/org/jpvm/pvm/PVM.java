package org.jpvm.pvm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.*;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.yaml.snakeyaml.Yaml;

public class PVM {

  /** thread state of each thread */
  public static ThreadLocal<ThreadState> tss = new ThreadLocal<>();

  /** initialize here for compatibility */
  public static InterpreterState interpreterState = new InterpreterState(500);
  /** filename of the py file to be executed. */
  private final String filename;
  /** code of the py file to be executed. */
  private PyCodeObject code;
  /** global and local variables of the py file to be executed. */
  private PyDictObject globals;

  private PyDictObject locals;
  private PyDictObject builtins;
  private PyModuleObject rootModule;
  private PyFrameObject rootFrame;
  private EvaluationLoop loop;

  public PVM(String filename) throws PyException, IOException {
    this.filename = filename;
    loadCode();
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
    // init type and object
    PyObject.initBaseObject();
    // init thread and vm state
    init();
    // init built-in functions
    BuiltIn.doInit();
    // acquire builtins
    builtins = BuiltIn.dict;
    /*
     * in main module locals and globals are the same
     */
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
        .addSearchPath(PyUnicodeObject.getOrCreateFromInternStringPool("org/jpvmExt/", true));
    var coFileName = ((PyUnicodeObject) code.getCoFileName()).getData();
    File file = new File(coFileName.replace("\\", "/"));
    String base = file.getParent();
    base = Paths.get(base).toAbsolutePath().toString();
    PVM.getThreadState().getIs().addSearchPath(new PyUnicodeObject(base + "/__pycache__"));
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
    rootFrame = new PyFrameObject(code, builtins, globals, locals);
    ThreadState ts = PVM.getThreadState();
    ts.setCurrentFrame(rootFrame);
    loop = new EvaluationLoop(rootFrame);
    loop.pyEvalFrame();
  }

  public void exit() {
    tss = null;
    interpreterState = null; // help gc
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
}
