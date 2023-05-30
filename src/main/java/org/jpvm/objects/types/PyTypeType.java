package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.pyinterface.TypeNew;

public class PyTypeType extends PyObject implements TypeNew {

  public static PyObject type = new PyTypeType();
  private final PyUnicodeObject name;

  public PyTypeType() {
    name = new PyUnicodeObject("type");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

  @Override
  public PyObject getType() {
    return type;
  }
}
