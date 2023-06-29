package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.objects.types.PyModuleType;

public class PyModuleObject extends PyObject {

  public static PyObject type = new PyModuleType();

  /** The module name. */
  private PyUnicodeObject moduleName;

  private PyModuleObject() {
    dict = new PyDictObject();
  }

  public PyModuleObject(PyUnicodeObject moduleName) {
    this();
    this.moduleName = moduleName;
  }

  public PyUnicodeObject getModuleName() {
    return moduleName;
  }

  public void setModuleName(PyUnicodeObject moduleName) {
    this.moduleName = moduleName;
  }

  @Override
  public String toString() {
    return moduleName.getData();
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
    return moduleName;
  }

  @Override
  public PyUnicodeObject repr() {
    return moduleName;
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    return moduleName.richCompare(o, op);
  }

  @Override
  public PyObject getMethod(String name) throws PyException {
    PyUnicodeObject object = new PyUnicodeObject(name);
    PyObject res = dict.get(object);
    if (res instanceof PyFunctionObject) return res;
    return null;
  }

  @Override
  public PyObject getAttr(PyObject key) throws PyException {
    return dict.get(key);
  }
}
