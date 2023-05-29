package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyTypeType extends PyObject {

  public static Object parentType = new PyTypeType();
  private final PyUnicodeObject name;

  public PyTypeType() {
    name = new PyUnicodeObject("type");
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
