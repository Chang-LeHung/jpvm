package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyIndexOutOfBoundErrorType extends PyExceptionType {
  public PyIndexOutOfBoundErrorType() {
    name = "IndexError";
    addBase(0, PyErrorUtils.Exception);
  }
}
