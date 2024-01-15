package org.jpvm.exceptions;

import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.types.PyBaseExceptionType;
import org.jpvm.exceptions.types.PyCommonExceptionType;
import org.jpvm.exceptions.types.PyExceptionType;
import org.jpvm.objects.PyObject;
import org.jpvm.python.BuiltIn;

public class ExceptionInfo {

  /** {@link PyCommonExceptionType} */
  private PyCommonExceptionType excType;
  /** {@link PyExceptionObject} */
  private PyExceptionObject excValue;
  /** {@link PyTraceBackObject} */
  private PyTraceBackObject excTrace;

  private ExceptionInfo previous;

  public ExceptionInfo(
      PyBaseExceptionType excType,
      PyExceptionObject excValue,
      ExceptionInfo previous,
      PyTraceBackObject excTrace) {
    this.excType = excType;
    this.excValue = excValue;
    this.previous = previous;
    this.excTrace = excTrace;
  }

  public ExceptionInfo() {}

  public PyCommonExceptionType getExcType() {
    return excType;
  }

  public void setExcType(PyObject excType) {
    if (excType != null && excType != BuiltIn.None) this.excType = (PyCommonExceptionType) excType;
  }

  public ExceptionInfo getPrevious() {
    return previous;
  }

  public void setPrevious(ExceptionInfo previous) {
    this.previous = previous;
  }

  public PyTraceBackObject getExcTrace() {
    return excTrace;
  }

  public void setExcTrace(PyTraceBackObject excTrace) {
    this.excTrace = excTrace;
  }

  public PyExceptionObject getExcValue() {

    return excValue;
  }

  public void setExcValue(PyExceptionObject excValue) {
    this.excValue = excValue;
  }
}
