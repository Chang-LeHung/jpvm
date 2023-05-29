package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyBaseObjectType extends PyTypeType {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyBaseObjectType() {
    name = new PyUnicodeObject("object");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }


  @Override
  public Object getType() {
    return PyTypeType.parentType;
  }
}
