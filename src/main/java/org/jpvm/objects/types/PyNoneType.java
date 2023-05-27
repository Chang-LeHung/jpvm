package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyNoneType extends PyObject {

  private final PyUnicodeObject name;

  public static Object parentType = PyTypeType.parentType;

  public PyNoneType() {
    name = new PyUnicodeObject("none");
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
