package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyComplexType extends PyTypeType {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyComplexType() {
    name = new PyUnicodeObject("complex");
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
