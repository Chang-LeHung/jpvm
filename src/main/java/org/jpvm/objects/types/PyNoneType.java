package org.jpvm.objects.types;

import org.jpvm.objects.PyNoneObject;

public class PyNoneType extends PyTypeType {
  public PyNoneType() {
    super(PyNoneObject.class);
    name = "none";
  }
}
