package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyFrameType extends PyObject {

  private final PyUnicodeObject name;

  public static Object parentType = PyTypeType.parentType;

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
