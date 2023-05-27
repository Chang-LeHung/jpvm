package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyBoolType  extends PyObject {

  private final PyUnicodeObject name;
  public Object parentType = PyTypeType.parentType;

  public PyBoolType() {
    name = new PyUnicodeObject("bool");
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
