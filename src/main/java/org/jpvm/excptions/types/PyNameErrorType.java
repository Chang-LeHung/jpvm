package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyNameErrorType extends PyPythonBaseExceptionType {

  public PyNameErrorType() {
    name = "NameError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
