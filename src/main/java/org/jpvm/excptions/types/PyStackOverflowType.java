package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyStackOverflowType extends PyPythonBaseExceptionType {

  public PyStackOverflowType() {
    name = "StackOverflow";
    addBase(0, PyErrorUtils.Exception);
  }
}
