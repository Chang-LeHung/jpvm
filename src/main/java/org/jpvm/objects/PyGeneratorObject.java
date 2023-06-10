package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.types.PyGeneratorType;
import org.jpvm.pvm.EvaluationLoop;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.python.BuiltIn;

public class PyGeneratorObject extends PyObject implements TypeDoIterate {

  public static PyObject type = new PyGeneratorType();

  /**
   * frame object {@link PyFrameObject} backing of this generator, which is runtime
   * environment of the generator
   */
  private final PyFrameObject frame;
  /**
   * name {@link PyUnicodeObject} of the generator
   */
  private PyObject name;
  /**
   * qualname {@link PyUnicodeObject} of the generator, which is more accurate than name
   * there are more qualifier than name, such as module name.
   * You can find more detail in <a href="https://stackoverflow.com/questions/58108488/what-is-qualname-in-python">qualname</a>
   */
  private PyObject qualname;

  /**
   * {@link PyCodeObject} of this generator
   */
  private final PyObject codeObject;

  private final EvaluationLoop evalLoop;


  public PyGeneratorObject(PyFrameObject frame) {
    this.frame = frame;
    codeObject = frame.getCode();
    evalLoop = new EvaluationLoop(frame);
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
    if (!evalLoop.getIterator().hasNext())
      return BuiltIn.PyExcStopIteration;
    return evalLoop.pyEvalFrame();
  }

  @Override
  public PyObject next() throws PyException {
    return __next__();
  }

  @Override
  public String toString() {
    return "PyGeneratorObject{" +
        "frame=" + frame +
        ", qualname=" + qualname +
        '}';
  }
}
