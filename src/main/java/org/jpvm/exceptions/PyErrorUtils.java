package org.jpvm.exceptions;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.types.*;
import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.vm.JPVM;
import org.jpvm.vm.ThreadState;

public class PyErrorUtils {

  public static final PyBaseExceptionType BaseException = PyBaseExceptionType.getInstance();
  public static final PyExceptionType Exception = PyExceptionType.getInstance();
  public static final PyAssertionErrorType AssertionError = PyAssertionErrorType.getInstance();
  public static final PyAttributeErrorType AttributeError = PyAttributeErrorType.getInstance();
  public static final PyKeyErrorType KeyError = PyKeyErrorType.getInstance();
  public static final PyNameErrorType NameError = PyNameErrorType.getInstance();
  public static final PyNotImplementedErrorType NotImplementedError =
      PyNotImplementedErrorType.getInstance();
  public static final PyRuntimeErrorType RuntimeError = PyRuntimeErrorType.getInstance();
  public static final PyTypeErrorType TypeError = PyTypeErrorType.getInstance();
  public static final PyValueErrorType ValueError = PyValueErrorType.getInstance();
  public static final PyZeroDivisionErrorType ZeroDivisionError =
      PyZeroDivisionErrorType.getInstance();
  public static final PyStackOverflowType StackOverflowError = PyStackOverflowType.getInstance();
  public static final PyImportErrorType ImportError = new PyImportErrorType();
  public static final PyIndexOutOfBoundErrorType IndexError =
      PyIndexOutOfBoundErrorType.getInstance();
  public static final PyFileNotFoundErrorType FileNotFoundError =
      PyFileNotFoundErrorType.getInstance();
  public static final PyStopIterationType StopIteration = PyStopIterationType.getInstance();

  public static PyObject pyErrorFormat(PyCommonExceptionType type, String msg) throws PyException {
    // create an instance of the exception
    PyTupleObject args = new PyTupleObject(1);
    args.set(0, new PyUnicodeObject(msg));
    PyExceptionObject value = type.call(args, null);
    ThreadState ts = JPVM.getThreadState();
    PyExceptionObject curExcValue = ts.getExceptionInfo().getExcValue();
    value.setContext(curExcValue);
    ts.setCurExcValue(value);
    ts.setCurExcType(type);
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
      System.err.print("Traceback (most recent call last):\n");
      recursivePrintExceptionInformation(ts.getCurExcValue());
    }
    System.err.print("\033[0m");
    System.err.flush();
  }

  private static boolean recursivePrintExceptionInformation(PyExceptionObject value) {
    if (value != null) {
      PyExceptionObject context = value.getContext();
      boolean p = recursivePrintExceptionInformation(context);
      if (p)
        System.err
            .println("\nDuring handling of the above exception, another exception occurred:\n");
      System.err.print(value.getTraceback().repr());
      System.err.print(value.getTypeName());
      System.err.print(": ");
      System.err.println(value.getErrorMsg());
      return true;
    }
    return false;
  }

  public static boolean isExceptionClass(PyObject object) {
    if (object instanceof PyTypeType type) {
      try {
        PyListObject mro = type.getMro();
        for (int i = 0; i < mro.size(); i++) {
          if (mro.get(i) == PyErrorUtils.BaseException)
            return true;
        }
      } catch (PyException ignore) {
      }
    }
    return false;
  }

  public static void restoreExceptionState(PyCommonExceptionType type, PyExceptionObject val,
      PyTraceBackObject tb) {
    ThreadState ts = JPVM.getThreadState();
    ts.setCurExcType(type);
    ts.setCurExcValue(val);
    ts.setCurExcTrace(tb);
  }

  public static void pyTraceBackHere() {
    ThreadState ts = JPVM.getThreadState();
    PyTraceBackObject tb = getTraceback();
    tb.setNext(ts.getCurExcTrace());
    ts.setCurExcTrace(tb);
    ts.getExceptionInfo().setExcTrace(tb);
    ts.getCurExcValue().setTraceback(tb);
  }

  public static boolean raiseException(PyObject exc, PyObject cause) throws PyException {
    ThreadState ts = JPVM.getThreadState();
    if (exc == null) {
      ExceptionInfo exceptionInfo = ts.getExceptionInfo();
      ts.setCurExcValue(exceptionInfo.getExcValue());
      ts.setCurExcType(exceptionInfo.getExcType());
      ts.setCurExcTrace(exceptionInfo.getExcTrace());
      return true;
    }
    PyCommonExceptionType type = null;
    if (exc instanceof PyCommonExceptionType excType) {
      type = excType;
      exc = excType.call("");
    } else {
      type = (PyCommonExceptionType) exc.getType();
    }
    ts.setCurExcType(type);
    ts.setCurExcValue((PyExceptionObject) exc);
    ts.setCurExcTrace(ts.getExceptionInfo().getExcTrace());
    return false;
  }
}
