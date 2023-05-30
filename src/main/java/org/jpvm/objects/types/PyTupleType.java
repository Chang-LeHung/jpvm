package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyTupleType extends PyTypeType {
  private final PyUnicodeObject name;

  public PyTupleType() {
    name = new PyUnicodeObject("tuple");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
