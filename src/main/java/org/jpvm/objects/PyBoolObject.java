package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.types.PyBoolType;
import org.jpvm.protocols.PyNumberMethods;

public class PyBoolObject extends PyObject implements PyNumberMethods {

  public static PyObject type = new PyBoolType();

  private boolean bool;

  public PyBoolObject(boolean bool) {
    this.bool = bool;
  }

  public static PyBoolObject check(PyObject o) {
    return new PyBoolObject(o == type);
  }

  public boolean isBool() {
    return bool;
  }

  public void setBool(boolean bool) {
    this.bool = bool;
  }

  @Override
  public String toString() {
    if (bool)
      return "True";
    return "False";
  }

  @Override
  public Object toJavaType() {
    return bool;
  }

  public boolean isTrue() {
    return bool;
  }

  public boolean isFalse() {
    return !bool;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyObject and(PyObject o) {
    if (!(o instanceof PyBoolObject))
      return new PyBoolObject(false);
    return new PyBoolObject(bool && ((PyBoolObject) o).bool);
  }

  @Override
  public PyObject xor(PyObject o) {
    if (!(o instanceof PyBoolObject))
      return new PyBoolObject(false);
    return new PyBoolObject(bool != ((PyBoolObject) o).bool);
  }

  @Override
  public PyObject or(PyObject o) {
    if (!(o instanceof PyBoolObject))
      return new PyBoolObject(false);
    return new PyBoolObject(bool || ((PyBoolObject) o).bool);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (obj instanceof PyBoolObject)
      return bool == ((PyBoolObject) obj).bool;
    return false;
  }

  @Override
  public PyUnicodeObject str() {
    return PyUnicodeObject.getOrCreateFromInternStringPool(toString(), true);
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyObject bool() throws PyNotImplemented {
    return this;
  }

  @Override
  public PyObject inplaceAnd(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return and(o);
  }

  @Override
  public PyObject inplaceXor(PyObject o) throws PyException {
    return xor(o);
  }

  @Override
  public PyObject inplaceOr(PyObject o) throws PyException {
    return or(o);
  }
}
