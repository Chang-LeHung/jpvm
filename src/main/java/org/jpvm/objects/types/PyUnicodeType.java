package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyUnicodeType extends PyObject {

  public static Object parentType = PyTypeType.parentType;
  private final PyUnicodeObject name;

  public PyUnicodeType() {
    this.name = new PyUnicodeObject("str");
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
