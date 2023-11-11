package org.jpvm.excptions;

import org.jpvm.excptions.types.PyBaseExceptionType;
import org.jpvm.objects.PyObject;

public class ExceptionInfo {

  /** {@link PyBaseExceptionType} */
  private PyObject curExcType;
  /** {@link PyPythonException} */
  private PyObject curExcValue;
  /** {@link PyTraceBackObject} */
  private PyObject curExcTrace;

  public ExceptionInfo(PyObject curExcType, PyObject curExcValue, PyObject curExcTrace) {
    this.curExcType = curExcType;
    this.curExcValue = curExcValue;
    this.curExcTrace = curExcTrace;
  }

  public ExceptionInfo() {}

  public PyObject getCurExcType() {
    return curExcType;
  }

  public void setCurExcType(PyObject curExcType) {
    this.curExcType = curExcType;
  }

  public PyObject getCurExcValue() {
    return curExcValue;
  }

  public void setCurExcValue(PyObject curExcValue) {
    this.curExcValue = curExcValue;
  }

  public PyObject getCurExcTrace() {
    return curExcTrace;
  }

  public void setCurExcTrace(PyObject curExcTrace) {
    this.curExcTrace = curExcTrace;
  }
}
