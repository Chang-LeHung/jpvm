package org.jpvm.objects;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.types.PySuperType;
import org.jpvm.objects.types.PyTypeType;

public class PySuperObject extends PyObject {
  public static final PyTypeType type = new PySuperType();

  private final PyObject self;
  private final PyObject cls;

  public PySuperObject(PyObject self, PyObject cls) {
    this.self = self;
    this.cls = cls;
  }

  @Override
  public String toString() {
    return type.getTypeName().getData();
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
    return new PyUnicodeObject("<super " + self.str() + ", " + cls.repr() + ", " + ">");
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyObject getMethod(PyUnicodeObject name) throws PyException {
    return getAttr(name);
  }

  @Override
  public PyObject getAttr(PyObject name) throws PyException {
    PyObject function = cls.getAttr(name);
    if (function instanceof PyFunctionObject func) {
      return new PyMethodObject(self, func, ((PyUnicodeObject) name).getData());
    }
    if (function instanceof PyMethodObject method) {
      method.setSelf(self);
      return method;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "super error: no such attribute");
    return null;
  }
}
