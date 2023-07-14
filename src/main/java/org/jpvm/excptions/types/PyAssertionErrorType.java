package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyAssertionErrorType extends PyPythonBaseExceptionType {

  public PyAssertionErrorType() {
    name = "AssertionError";
    addBase(0, PyErrorUtils.Exception);
  }
}
