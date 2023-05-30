package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyDictType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyDictType() {
    this.name = new PyUnicodeObject("dict");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
