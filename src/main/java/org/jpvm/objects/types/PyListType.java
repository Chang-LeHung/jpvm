package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyListType extends PyTypeType {
  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyListType() {
    name = new PyUnicodeObject("list");
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
