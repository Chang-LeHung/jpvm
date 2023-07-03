package org.jpvm.excptions.types;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.types.PyTypeType;

public class PyNotImplementedErrorType extends PyTypeType {

  public PyNotImplementedErrorType() {
    super(PyPythonException.class);
    name = "NotImplementedError";
    addBase(PyPythonException.type);
  }
}
