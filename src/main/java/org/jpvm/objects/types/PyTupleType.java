package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyTupleType extends PyTypeType {
  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyTupleType() {
    name = new PyUnicodeObject("tuple");
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
