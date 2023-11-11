package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyRuntimeErrorType extends PyExceptionType {
  public PyRuntimeErrorType() {
    name = "RuntimeError";
    addBase(0, PyErrorUtils.Exception);
  }
}
