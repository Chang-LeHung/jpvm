package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyFloatType extends PyTypeType {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyFloatType() {
    name = new PyUnicodeObject("float");
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
