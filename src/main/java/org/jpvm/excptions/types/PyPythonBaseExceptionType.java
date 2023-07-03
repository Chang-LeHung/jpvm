package org.jpvm.excptions.types;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.types.PyTypeType;

public class PyPythonBaseExceptionType extends PyTypeType {

  public PyPythonBaseExceptionType() {
    super(PyPythonException.class);
    name = "BaseException";
    addBase(PyPythonException.type);
  }
}
