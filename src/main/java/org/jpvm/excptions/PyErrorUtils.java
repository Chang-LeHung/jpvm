package org.jpvm.excptions;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.types.*;
import org.jpvm.module.sys.Sys;
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

  public static PyObject pyErrorFormat(PyPythonBaseExceptionType type, String msg)
      throws PyException {
    // create an instance of the exception
    PyPythonException call = type.call(msg);
    ThreadState ts = PVM.getThreadState();
    call.setPreviousExceptionInfo(ts.getExceptionInfo());
    ts.setCurExcType(type);
    ts.setCurExcValue(call);
    ts.setCurExcTrace(getTraceback());
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
    ExceptionInfo exceptionInfo = ts.getExceptionInfo();
    PyObject curExcType = ts.getCurExcType();
    if (curExcType != null) {
      PyObject curExcTrace = ts.getCurExcTrace();
      System.err.print(curExcTrace.repr());
      System.err.print(curExcType.getTypeName());
      System.err.print(": ");
      var curExcValue = (PyPythonException) ts.getCurExcValue();
      System.err.println(curExcValue.getExceptionInformation());
    }
    while (exceptionInfo != null) {
      var curExcValue = (PyPythonException) exceptionInfo.getCurExcValue();
      if (curExcValue != null) {
        PyTraceBackObject traceBack = curExcValue.getTraceBack();
        System.err.print(traceBack.repr());
        exceptionInfo = curExcValue.getPreviousExceptionInfo();
        if (exceptionInfo != null)
          System.err.println("During handling of the above exception, another exception occurred:");
      } else break;
    }
    System.err.print("\033[0m");
    System.err.flush();
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
}
