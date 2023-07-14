package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyAttributeErrorType extends PyPythonBaseExceptionType {

  public PyAttributeErrorType() {
    name = "AttributeError";
    addBase(0, PyErrorUtils.Exception);
  }

  @Override
  public String toString() {
    return "AttributeError";
  }
}
