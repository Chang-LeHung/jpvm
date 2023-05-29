package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PySliceType extends PyTypeType {
  private final PyUnicodeObject name;
  public Object parentType = PyTypeType.parentType;

  public PySliceType() {
    name = new PyUnicodeObject("slice");
  }

  @Override
  public Object getType() {
    return parentType;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }
}
