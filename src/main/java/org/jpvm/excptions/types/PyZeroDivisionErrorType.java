package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyZeroDivisionErrorType extends PyExceptionType {
  public PyZeroDivisionErrorType() {
    name = "ZeroDivisionError";
    addBase(0, PyErrorUtils.Exception);
  }
}
