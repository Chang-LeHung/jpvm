package org.jpvm.exceptions.types;

import org.jpvm.exceptions.pyobjs.PyExceptionObject;

public class PyBaseExceptionType extends PyCommonExceptionType {

  private PyBaseExceptionType() {
    super(PyExceptionObject.class);
    name = "BaseException";
  }

  public static final class SelfHolder {
    public static final PyBaseExceptionType self = new PyBaseExceptionType();
  }

  public static PyBaseExceptionType getInstance() {
    return SelfHolder.self;
  }
}
