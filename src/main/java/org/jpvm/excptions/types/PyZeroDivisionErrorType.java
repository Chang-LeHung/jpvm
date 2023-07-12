package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyZeroDivisionErrorType extends PyPythonBaseExceptionType {
  public PyZeroDivisionErrorType() {
    name = "ZeroDivisionError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
