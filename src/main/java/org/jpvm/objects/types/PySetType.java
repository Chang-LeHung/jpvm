package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PySetType extends PyTypeType {
  private final PyUnicodeObject name;

  public PySetType() {
    name = new PyUnicodeObject("set");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
