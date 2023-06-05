package org.jpvm.objects;

import org.jpvm.objects.types.PyNoneType;

public class PyNoneObject extends PyObject {

  public static PyObject type = new PyNoneType();

  public static PyBoolObject check(PyObject o) {
    return new PyBoolObject(o == type);
  }

  @Override
  public String toString() {
    return "None";
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return PyUnicodeObject.getOrCreateFromInternStringPool("None", true);
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }
}
