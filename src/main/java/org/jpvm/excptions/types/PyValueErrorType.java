package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyValueErrorType extends PyExceptionType {
  public PyValueErrorType() {
    name = "ValueError";
    addBase(0, PyErrorUtils.Exception);
  }
}
