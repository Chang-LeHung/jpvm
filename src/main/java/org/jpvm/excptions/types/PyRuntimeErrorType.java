package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyRuntimeErrorType extends PyPythonBaseExceptionType {
  public PyRuntimeErrorType() {
    name = "RuntimeError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }
}
