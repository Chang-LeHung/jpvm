package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyAssertionErrorType extends PyExceptionType {

  public PyAssertionErrorType() {
    name = "AssertionError";
    addBase(0, PyErrorUtils.Exception);
  }
}
