package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyKeyErrorType extends PyPythonBaseExceptionType {

  public PyKeyErrorType() {
    name = "KeyError";
    addBase(0, PyErrorUtils.Exception);
  }
}
