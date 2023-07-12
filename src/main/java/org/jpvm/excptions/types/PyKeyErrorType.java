package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyKeyErrorType extends PyPythonBaseExceptionType {

  public PyKeyErrorType() {
    name = "KeyError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
