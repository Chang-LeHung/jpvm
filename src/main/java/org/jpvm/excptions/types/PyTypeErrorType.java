package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyTypeErrorType extends PyPythonBaseExceptionType {
  public PyTypeErrorType() {
    name = "TypeError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
