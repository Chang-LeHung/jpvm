package org.jpvm.objects.types;

import org.jpvm.objects.PyFunctionObject;

public class PyFunctionType extends PyTypeType {
  private PyFunctionType() {
    super(PyFunctionObject.class);
    name = "function";
  }


  public static final class SelfHolder {
    public static final PyFunctionType self = new PyFunctionType();
  }

  public static PyFunctionType getInstance() {
    return SelfHolder.self;
  }
}
