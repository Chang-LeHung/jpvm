package org.jpvm.excptions;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.types.*;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyTraceBackObject;
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

  public static PyImportErrorType ImportError = new PyImportErrorType();

  public static void pyErrorFormat(PyPythonBaseExceptionType type, String msg) throws PyException {
    PyPythonException call = type.call(msg);
    ThreadState ts = PVM.getThreadState();
    ts.setCurExcType(type);
    ts.setCurExcValue(call);
    ts.setCurExcTrace(getTraceback(call));
    throw new PyException(msg);
  }

  public static PyTraceBackObject getTraceback(PyPythonException call) {
    ThreadState ts = PVM.getThreadState();
    PyFrameObject currentFrame = ts.getCurrentFrame();
    PyTraceBackObject tb = new PyTraceBackObject(call);
    while (currentFrame != null) {
      tb.add(currentFrame);
      currentFrame = currentFrame.getBack();
    }
    return tb;
  }
}
