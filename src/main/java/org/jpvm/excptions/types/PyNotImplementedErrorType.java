package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyNotImplementedErrorType extends PyPythonBaseExceptionType {

  public PyNotImplementedErrorType() {
    name = "NotImplementedError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
