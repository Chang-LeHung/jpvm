package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyMethodType extends PyTypeType {
  private final PyUnicodeObject name;

  public PyMethodType() {
    this.name = new PyUnicodeObject("method");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
