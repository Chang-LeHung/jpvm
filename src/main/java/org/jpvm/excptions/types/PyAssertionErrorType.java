package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyAssertionErrorType extends PyPythonBaseExceptionType {

  public PyAssertionErrorType() {
    name = "AssertionError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
