package org.jpvm.excptions.types;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.types.PyTypeType;

public class PyAssertionErrorType extends PyTypeType {

  public PyAssertionErrorType() {
    super(PyPythonException.class);
    name = "AssertionError";
    addBase(PyPythonException.type);
  }
}
