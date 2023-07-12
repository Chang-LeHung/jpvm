package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyExceptionType extends PyPythonBaseExceptionType {

  public PyExceptionType() {
    name = "Exception";
    addBase(0, BuiltIn.loadFromDict("BaseException"));
  }
}
