package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyNameErrorType extends PyExceptionType {

  public PyNameErrorType() {
    name = "NameError";
    addBase(0, PyErrorUtils.Exception);
  }
}
