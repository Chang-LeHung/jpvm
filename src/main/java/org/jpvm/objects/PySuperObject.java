package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.types.PySuperType;

public class PySuperObject extends PyObject {
  public static PyObject type = new PySuperType();

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
    PyObject function = cls.getAttr(name);
    if (function instanceof PyFunctionObject func) {
      return new PyMethodObject(self, func, name.getData());
    }
    if (function instanceof PyMethodObject method) {
      method.setSelf(self);
      return method;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "super error: no such attribute");
    return null;
  }
}
