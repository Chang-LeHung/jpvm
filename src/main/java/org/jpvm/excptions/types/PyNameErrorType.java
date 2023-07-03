package org.jpvm.excptions.types;

import org.jpvm.errors.PyNameError;
import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.types.PyTypeType;

public class PyNameErrorType extends PyTypeType {

  public PyNameErrorType() {
    super(PyNameError.class);
    addBase(PyPythonException.type);
    name = "NameError";
  }
}
