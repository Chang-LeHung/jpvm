package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyUnicodeType extends PyTypeType {
  private final PyUnicodeObject name;

  public PyUnicodeType() {
    this.name = new PyUnicodeObject("str");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }
}
