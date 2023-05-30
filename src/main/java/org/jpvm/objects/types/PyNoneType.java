package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyNoneType extends PyTypeType {
  private final PyUnicodeObject name;

  public PyNoneType() {
    name = new PyUnicodeObject("none");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
