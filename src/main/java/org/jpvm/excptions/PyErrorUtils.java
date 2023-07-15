package org.jpvm.excptions;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.types.*;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.pvm.PVM;
import org.jpvm.pvm.ThreadState;

public class PyErrorUtils {

  public static PyPythonBaseExceptionType BaseException = new PyPythonBaseExceptionType();

  public static PyPythonBaseExceptionType Exception = new PyExceptionType();
  public static PyPythonBaseExceptionType AssertionError = new PyAssertionErrorType();
  public static PyPythonBaseExceptionType AttributeError = new PyAttributeErrorType();
  public static PyPythonBaseExceptionType KeyError = new PyKeyErrorType();
  public static PyPythonBaseExceptionType NameError = new PyNameErrorType();
  public static PyPythonBaseExceptionType NotImplementedError = new PyNotImplementedErrorType();
  public static PyPythonBaseExceptionType RuntimeError = new PyRuntimeErrorType();
  public static PyPythonBaseExceptionType TypeError = new PyTypeErrorType();
  public static PyPythonBaseExceptionType ValueError = new PyValueErrorType();
  public static PyPythonBaseExceptionType ZeroDivisionError = new PyZeroDivisionErrorType();

  public static PyPythonBaseExceptionType StackOverflowError = new PyStackOverflowType();

  public static PyImportErrorType ImportError = new PyImportErrorType();
  public static PyIndexOutOfBoundErrorType IndexError = new PyIndexOutOfBoundErrorType();

  public static PyObject pyErrorFormat(PyPythonBaseExceptionType type, String msg)
      throws PyException {
    // create an instance of the exception
    PyPythonException call = type.call(msg);
    ThreadState ts = PVM.getThreadState();
    call.setContext((PyPythonException) ts.getExceptionInfo().getCurExcValue());
    ts.setCurExcType(type);
    ts.setCurExcValue(call);
    throw new PyException(msg);
  }

  public static PyTraceBackObject getTraceback() {
    ThreadState ts = PVM.getThreadState();
    PyFrameObject currentFrame = ts.getCurrentFrame();
    return new PyTraceBackObject(currentFrame);
  }

  public static void cleanThreadException() {
    ThreadState ts = PVM.getThreadState();
    ts.setCurExcTrace(null);
    ts.setCurExcValue(null);
    ts.setCurExcType(null);
  }

  public static void printExceptionInformation() {
    System.err.print("\033[31m");
    ThreadState ts = PVM.getThreadState();
    PyObject curExcType = ts.getCurExcType();
    if (curExcType != null) {
      var curExcValue = (PyPythonException) ts.getCurExcValue();
      System.err.print("Traceback (most recent call last):\n");
      recursivePrintExceptionInformation(curExcValue);
    }
    System.err.print("\033[0m");
    System.err.flush();
  }

  private static boolean recursivePrintExceptionInformation(PyPythonException excValue) {
    if (excValue != null) {
      PyPythonException context = excValue.getContext();
      boolean p = recursivePrintExceptionInformation(context);
      if (p)
        System.err.println(
            "\nDuring handling of the above exception, another exception occurred:\n");
      System.err.print(excValue.getTraceBack().repr());
      System.err.print(excValue.getType().getTypeName());
      System.err.print(": ");
      System.err.println(excValue.getExceptionInformation());
      return true;
    }
    return false;
  }

  public static boolean isExceptionClass(PyObject object) {
    if (object instanceof PyTypeType type) {
      try {
        PyTupleObject mro = type.getMro();
        for (int i = 0; i < mro.size(); i++) {
          if (mro.get(i) == PyErrorUtils.BaseException) return true;
        }
      } catch (PyException ignore) {
      }
    }
    return false;
  }

  public static void restoreExceptionState(PyObject type, PyObject val, PyObject tb) {
    ThreadState ts = PVM.getThreadState();
    ts.setCurExcType(type);
    ts.setCurExcValue(val);
    ts.setCurExcTrace(tb);
  }

  public static void pyTraceBackHere() {
    ThreadState ts = PVM.getThreadState();
    PyTraceBackObject tb = getTraceback();
    tb.setNext((PyTraceBackObject) ts.getCurExcTrace());
    ts.setCurExcTrace(tb);
    ((PyPythonException) ts.getCurExcValue()).setTraceBack(tb);
  }
}
