package org.jpvm.excptions;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.types.*;
import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.vm.JPVM;
import org.jpvm.vm.ThreadState;

public class PyErrorUtils {

  public static PyBaseExceptionType BaseException = new PyBaseExceptionType();

  public static PyBaseExceptionType Exception = new PyExceptionType();
  public static PyBaseExceptionType AssertionError = new PyAssertionErrorType();
  public static PyBaseExceptionType AttributeError = new PyAttributeErrorType();
  public static PyBaseExceptionType KeyError = new PyKeyErrorType();
  public static PyBaseExceptionType NameError = new PyNameErrorType();
  public static PyBaseExceptionType NotImplementedError = new PyNotImplementedErrorType();
  public static PyBaseExceptionType RuntimeError = new PyRuntimeErrorType();
  public static PyBaseExceptionType TypeError = new PyTypeErrorType();
  public static PyBaseExceptionType ValueError = new PyValueErrorType();
  public static PyBaseExceptionType ZeroDivisionError = new PyZeroDivisionErrorType();

  public static PyBaseExceptionType StackOverflowError = new PyStackOverflowType();

  public static PyImportErrorType ImportError = new PyImportErrorType();
  public static PyIndexOutOfBoundErrorType IndexError = new PyIndexOutOfBoundErrorType();
  public static PyFileNotFoundErrorType FileNotFoundError = new PyFileNotFoundErrorType();
  public static PyStopIterationType StopIteration = new PyStopIterationType();

  public static PyObject pyErrorFormat(PyBaseExceptionType type, String msg) throws PyException {
    // create an instance of the exception
    PyTupleObject args = new PyTupleObject(1);
    args.set(0, new PyUnicodeObject(msg));
    PyExceptionContext call = type.call(args, null);
    ThreadState ts = JPVM.getThreadState();
    call.setContext((PyExceptionContext) ts.getExceptionInfo().getCurExcValue());
    ts.setCurExcType(type);
    ts.setCurExcValue(call);
    throw new PyException(msg);
  }

  public static PyTraceBackObject getTraceback() {
    ThreadState ts = JPVM.getThreadState();
    PyFrameObject currentFrame = ts.getCurrentFrame();
    return new PyTraceBackObject(currentFrame);
  }

  public static void cleanThreadException() {
    ThreadState ts = JPVM.getThreadState();
    ts.setCurExcTrace(null);
    ts.setCurExcValue(null);
    ts.setCurExcType(null);
  }

  public static void printExceptionInformation() {
    System.err.print("\033[31m");
    ThreadState ts = JPVM.getThreadState();
    PyObject curExcType = ts.getCurExcType();
    if (curExcType != null) {
      var curExcValue = (PyExceptionContext) ts.getCurExcValue();
      System.err.print("Traceback (most recent call last):\n");
      recursivePrintExceptionInformation(curExcValue);
    }
    System.err.print("\033[0m");
    System.err.flush();
  }

  private static boolean recursivePrintExceptionInformation(PyExceptionContext excValue) {
    if (excValue != null) {
      PyExceptionContext context = excValue.getContext();
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
        PyListObject mro = type.getMro();
        for (int i = 0; i < mro.size(); i++) {
          if (mro.get(i) == PyErrorUtils.BaseException) return true;
        }
      } catch (PyException ignore) {
      }
    }
    return false;
  }

  public static void restoreExceptionState(PyObject type, PyObject val, PyObject tb) {
    ThreadState ts = JPVM.getThreadState();
    ts.setCurExcType(type);
    ts.setCurExcValue(val);
    ts.setCurExcTrace(tb);
  }

  public static void pyTraceBackHere() {
    ThreadState ts = JPVM.getThreadState();
    PyTraceBackObject tb = getTraceback();
    tb.setNext((PyTraceBackObject) ts.getCurExcTrace());
    ts.setCurExcTrace(tb);
    ((PyExceptionContext) ts.getCurExcValue()).setTraceBack(tb);
  }

  public static boolean raiseException(PyObject exc, PyObject cause) throws PyException {
    ThreadState ts = JPVM.getThreadState();
    if (exc == null) {
      ExceptionInfo exceptionInfo = ts.getExceptionInfo();
      ts.setCurExcValue(exceptionInfo.getCurExcValue());
      ts.setCurExcType(exceptionInfo.getCurExcType());
      ts.setCurExcTrace(exceptionInfo.getCurExcTrace());
      return true;
    }
    PyBaseExceptionType type = null;
    if (exc instanceof PyExceptionContext pyExc) {
      type = (PyBaseExceptionType) pyExc.getType();
    } else if (exc instanceof PyBaseExceptionType excType) {
      type = excType;
      exc = excType.call("");
    }
    ts.setCurExcType(type);
    ts.setCurExcValue(exc);
    assert exc instanceof PyExceptionContext;
    ts.setCurExcTrace(((PyExceptionContext) exc).getTraceBack());
    return false;
  }
}
