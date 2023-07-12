package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyAttributeErrorType extends PyPythonBaseExceptionType {

  public PyAttributeErrorType() {
    name = "AttributeError";
    addBase(0, BuiltIn.loadFromDict("Exception"));
  }

  @Override
  public String toString() {
    return "AttributeError";
  }
}
