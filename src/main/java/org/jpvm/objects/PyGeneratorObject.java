package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyGeneratorType;
import org.jpvm.pvm.EvaluationLoop;
import org.jpvm.pvm.PVM;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.python.BuiltIn;

public class PyGeneratorObject extends PyObject implements TypeDoIterate, TypeIterable {

  public static PyObject type = new PyGeneratorType();

  /**
   * frame object {@link PyFrameObject} backing of this generator, which is runtime environment of
   * the generator
   */
  private final PyFrameObject frame;
  /** {@link PyCodeObject} of this generator */
  private final PyObject codeObject;

  private final EvaluationLoop evalLoop;
  /**
   * qualname {@link PyUnicodeObject} of the generator, which is more accurate than name there are
   * more qualifier than name, such as module name. You can find more detail in <a
   * href="https://stackoverflow.com/questions/58108488/what-is-qualname-in-python">qualname</a>
   */
  private final PyObject qualname;
  /** name {@link PyUnicodeObject} of the generator */
  private PyObject name;

  private boolean runToYield = false;
  private boolean newVal = false;
  private PyObject yieldValue = BuiltIn.None;

  public PyGeneratorObject(PyFrameObject frame) {
    this.frame = frame;
    codeObject = frame.getCode();
    evalLoop = new EvaluationLoop(frame);
    qualname = frame.getCode().getCoName();
  }

  public PyGeneratorObject(PyFrameObject frame, PyObject name, PyObject qualname) {
    this.frame = frame;
    this.name = name;
    this.qualname = qualname;
    codeObject = frame.getCode();
    evalLoop = new EvaluationLoop(frame);
  }

  public PyFrameObject getFrame() {
    return frame;
  }

  public PyObject getName() {
    return name;
  }

  public PyObject getQualname() {
    return qualname;
  }

  public PyObject getCodeObject() {
    return codeObject;
  }

  @PyClassMethod
  public PyObject __next__() throws PyException {
    // whether generator started or not
    if (!runToYield) runToYield = true;
    else {
      // push a dummy PyObject or sent value into stack
      // if generator runToYield = true
      if (newVal) {
        evalLoop.getFrame().setTop(1, yieldValue);
        newVal = false;
      } else {
        evalLoop.getFrame().setTop(1, BuiltIn.None);
      }
    }
    PyFrameObject cf = PVM.getThreadState().getCurrentFrame();
    PyFrameObject f = evalLoop.getFrame();
    PVM.getThreadState().setCurrentFrame(f);
    PyObject res = evalLoop.pyEvalFrame();
    PVM.getThreadState().setCurrentFrame(cf);
    if (!evalLoop.getIterator().hasNext()) return BuiltIn.PyExcStopIteration;
    else return res;
  }

  public boolean started() {
    return runToYield;
  }

  @PyClassMethod
  public PyObject send(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (!runToYield) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.Exception, "can't send non-None value to a just-started generator");
      return null;
    }
    if (args.size() == 1) {
      yieldValue = args.get(0);
      newVal = true;
      return __next__();
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.Exception, "TypeError: send() takes exactly one argument");
    return null;
  }

  @Override
  public PyObject next() throws PyException {
    return __next__();
  }

  @Override
  public String toString() {
    return "PyGeneratorObject{" + "qualname=" + qualname + '}';
  }

  @Override
  public TypeDoIterate getIterator() throws PyNotImplemented {
    return this;
  }

  public PyObject start(PyObject o) throws PyException {
    if (started()) {
      yieldValue = o;
      newVal = true;
    }
    return next();
  }
}
