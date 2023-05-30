package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyListType extends PyTypeType {
  private final PyUnicodeObject name;

  public PyListType() {
    name = new PyUnicodeObject("list");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
