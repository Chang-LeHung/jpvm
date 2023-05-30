package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyBytesType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyBytesType() {
    name = new PyUnicodeObject("bytes");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
