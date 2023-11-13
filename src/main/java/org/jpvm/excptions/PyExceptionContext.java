package org.jpvm.excptions;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTypeType;

public class PyExceptionContext extends PyObject {

  private final PyUnicodeObject exceptionInformation;
  private PyExceptionContext context;
  /** {@link PyTraceBackObject} */
  private PyTraceBackObject traceBack;

  public PyExceptionContext(PyTypeType type, PyUnicodeObject exceptionInformation) {
    this.objType = type;
    this.exceptionInformation = exceptionInformation;
  }

  public PyUnicodeObject getExceptionInformation() {
    return exceptionInformation;
  }

  @Override
  public PyObject getType() {
    return objType;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return objType.getTypeName();
  }

  @Override
  public String toString() {
    return "exception: " + exceptionInformation.toString();
  }

  @Override
  public PyUnicodeObject str() {
    return exceptionInformation;
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(name.getData() + "(" + exceptionInformation.repr() + ")");
  }

  public PyExceptionContext getContext() {
    return context;
  }

  public void setContext(PyExceptionContext context) {
    this.context = context;
  }

  public PyTraceBackObject getTraceBack() {
    return traceBack;
  }

  public void setTraceBack(PyTraceBackObject traceBack) {
    this.traceBack = traceBack;
  }
}
