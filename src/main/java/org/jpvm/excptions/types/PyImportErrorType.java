package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyImportErrorType extends PyPythonBaseExceptionType {

  public PyImportErrorType() {
    name = "ImportError";
    addBase(0, PyErrorUtils.Exception);
  }
}
