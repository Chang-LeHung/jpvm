package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.objects.types.PyTypeType;

/**
 * used in python code to represent a python object
 */
public class PyPythonObject extends PyObject {

  private PyTypeType type;
  public PyPythonObject() {
    dict = new PyDictObject();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public PyObject getType() {
    return type;
  }

  public void setType(PyTypeType type) {
    this.type = type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return super.str();
  }

  @Override
  public PyUnicodeObject repr() {
    return super.repr();
  }

  @Override
  public PyLongObject hash() {
    return super.hash();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    return super.richCompare(o, op);
  }

  @Override
  public PyDictObject getDict() {
    return super.getDict();
  }
}
