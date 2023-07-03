package org.jpvm.objects.types;

import org.jpvm.objects.PyNativeMethodObject;

public class PyNativeMethodType extends PyTypeType {

  public PyNativeMethodType() {
    super(PyNativeMethodObject.class);
    name = "native_method";
  }
}
