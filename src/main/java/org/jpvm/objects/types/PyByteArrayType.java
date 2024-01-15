package org.jpvm.objects.types;

import org.jpvm.objects.PyBytesObject;

public class PyByteArrayType extends PyTypeType {

  public PyByteArrayType() {
    super(PyBytesObject.class);
    name = "bytesarray";
  }
}
