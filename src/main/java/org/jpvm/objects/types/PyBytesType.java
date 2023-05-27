package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyBytesType extends PyObject {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyBytesType() {
    name = new PyUnicodeObject("bytes");
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
