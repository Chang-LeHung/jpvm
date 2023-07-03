package org.jpvm.excptions;

import org.jpvm.excptions.types.PyPythonBaseExceptionType;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyPythonException extends PyObject {

  public static PyObject type = new PyPythonBaseExceptionType();

  private final PyUnicodeObject exceptionInformation;

  public PyPythonException(PyUnicodeObject exceptionInformation) {
    this.exceptionInformation = exceptionInformation;
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
    return exceptionInformation;
  }
}
