package org.jpvm.exceptions.pyobjs;

import org.jpvm.exceptions.PyTraceBackObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyExceptionObject extends PyObject {

  private final PyUnicodeObject errorMsg;

  private PyTraceBackObject traceback;

  private PyExceptionObject context;

  private PyObject cause;

  public PyExceptionObject(PyUnicodeObject errorMsg) {
    this.errorMsg = errorMsg;
  }

  public PyExceptionObject(String errorMsg) {
    this.errorMsg = new PyUnicodeObject(errorMsg);
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(String.format("<PyExceptionObject object %s >", errorMsg.getData()));
  }

  @Override
  public PyUnicodeObject str() {
    return repr();
  }

  public PyUnicodeObject getErrorMsg() {
    return errorMsg;
  }

  public PyTraceBackObject getTraceback() {
    return traceback;
  }

  public void setTraceback(PyTraceBackObject traceback) {
    this.traceback = traceback;
  }

  public PyExceptionObject getContext() {
    return context;
  }

  public void setContext(PyExceptionObject context) {
    this.context = context;
  }

  public PyObject getCause() {
    return cause;
  }

  public void setCause(PyObject cause) {
    this.cause = cause;
  }

  @Override
  public PyObject getType() {
    return objType;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return objType.getTypeName();
  }
}
