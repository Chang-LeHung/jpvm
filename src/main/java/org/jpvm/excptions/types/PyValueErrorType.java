package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyValueErrorType extends PyPythonBaseExceptionType {
  public PyValueErrorType() {
    name = "ValueError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
