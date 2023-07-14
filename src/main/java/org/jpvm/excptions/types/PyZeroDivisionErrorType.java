package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyZeroDivisionErrorType extends PyPythonBaseExceptionType {
  public PyZeroDivisionErrorType() {
    name = "ZeroDivisionError";
    addBase(0, PyErrorUtils.Exception);
  }
}
