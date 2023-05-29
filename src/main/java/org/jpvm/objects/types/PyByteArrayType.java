package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyByteArrayType extends PyTypeType {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyByteArrayType() {
    name = new PyUnicodeObject("bytearray");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

  @Override
  public Object getType() {
    return parentType;
  }
}
