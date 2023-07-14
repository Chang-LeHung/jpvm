package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyTypeErrorType extends PyPythonBaseExceptionType {
  public PyTypeErrorType() {
    name = "TypeError";
    addBase(0, PyErrorUtils.Exception);
  }
}
