package org.jpvm.excptions;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTypeType;

public class PyPythonException extends PyObject {

  private final PyObject type;

  private final PyUnicodeObject exceptionInformation;

  public PyPythonException(PyTypeType type, PyUnicodeObject exceptionInformation) {
    this.type = type;
    this.exceptionInformation = exceptionInformation;
  }

  public PyUnicodeObject getExceptionInformation() {
    return exceptionInformation;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
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
}
