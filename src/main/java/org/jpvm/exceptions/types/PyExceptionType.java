package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;

public class PyExceptionType extends PyCommonExceptionType {

  private PyExceptionType() {
    super(PyExceptionObject.class);
    name = "Exception";
    addBase(0, PyErrorUtils.BaseException);
  }

  public static final class SelfHolder {
    public static final PyExceptionType self = new PyExceptionType();
  }

  public static PyExceptionType getInstance() {
    return SelfHolder.self;
  }
}
