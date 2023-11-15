package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;

public class PyExceptionType extends PyBaseExceptionType {

  public PyExceptionType() {
    name = "Exception";
    addBase(0, PyErrorUtils.BaseException);
  }
}
