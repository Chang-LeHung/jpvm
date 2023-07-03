package org.jpvm.objects.types;

import org.jpvm.objects.PyBytesObject;

public class PyBytesType extends PyTypeType {

  public PyBytesType() {
    super(PyBytesObject.class);
    name = "bytes";
  }
}
