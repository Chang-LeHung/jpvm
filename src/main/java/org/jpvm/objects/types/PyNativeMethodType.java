package org.jpvm.objects.types;

import org.jpvm.objects.PyNativeMethodObject;

public class PyNativeMethodType extends PyTypeType {

  private PyNativeMethodType() {
    super(PyNativeMethodObject.class);
    name = "native_method";
  }

  public static final class SelfHolder {
    public static final PyNativeMethodType self = new PyNativeMethodType();
  }

  public static PyNativeMethodType getInstance() {
    return SelfHolder.self;
  }
}
