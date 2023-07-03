package org.jpvm.excptions.types;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.types.PyTypeType;

public class PyKeyErrorType extends PyTypeType {

  public PyKeyErrorType() {
    super(PyPythonException.class);
    name = "KeyError";
    addBase(PyPythonException.type);
  }
}
