package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyExceptionType extends PyPythonBaseExceptionType {

  public PyExceptionType() {
    name = "Exception";
    addBase(0, PyErrorUtils.BaseException);
  }
}
