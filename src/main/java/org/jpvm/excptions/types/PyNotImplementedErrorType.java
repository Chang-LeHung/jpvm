package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyNotImplementedErrorType extends PyPythonBaseExceptionType {

  public PyNotImplementedErrorType() {
    name = "NotImplementedError";
    addBase(0, PyErrorUtils.Exception);
  }
}
