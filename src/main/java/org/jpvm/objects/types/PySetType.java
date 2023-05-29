package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PySetType extends PyTypeType {

  public static Object parentType = PyTypeType.parentType;
  private final PyUnicodeObject name;

  public PySetType() {
    name = new PyUnicodeObject("set");
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
