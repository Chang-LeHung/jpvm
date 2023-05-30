package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyByteArrayType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyByteArrayType() {
    name = new PyUnicodeObject("bytearray");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
