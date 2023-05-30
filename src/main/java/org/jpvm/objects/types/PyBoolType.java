package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyBoolType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyBoolType() {
    name = new PyUnicodeObject("bool");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
