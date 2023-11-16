package org.jpvm.objects;

import org.jpvm.objects.types.PyNoneType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.python.BuiltIn;

public class PyNoneObject extends PyObject {

  public static final PyTypeType type = PyNoneType.getInstance();

  public static PyBoolObject check(PyObject o) {
    return o == type ? BuiltIn.True : BuiltIn.False;
  }

  private PyNoneObject() {}

  public static class SelfHolder {
    public static final PyNoneObject self = new PyNoneObject();
  }

  public static PyNoneObject getInstance() {
    return SelfHolder.self;
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
