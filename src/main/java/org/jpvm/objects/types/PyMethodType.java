package org.jpvm.objects.types;

import org.jpvm.objects.PyMethodObject;

public class PyMethodType extends PyTypeType {
  public PyMethodType() {
    super(PyMethodObject.class);
    name = "method";
  }
}
