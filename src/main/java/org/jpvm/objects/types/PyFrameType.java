package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyFrameType extends PyTypeType {

  public static Object parentType = PyTypeType.parentType;
  private final PyUnicodeObject name;

  public PyFrameType() {
    name = new PyUnicodeObject("frame");
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
