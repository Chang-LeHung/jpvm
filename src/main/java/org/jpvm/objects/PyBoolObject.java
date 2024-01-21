package org.jpvm.objects;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.exceptions.jobjs.PyTypeNotMatch;
import org.jpvm.objects.types.PyBoolType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;

public class PyBoolObject extends PyObject implements PyNumberMethods {

  public static final PyTypeType type = PyBoolType.getInstance();

  private boolean bool;

  private PyBoolObject(boolean bool) {
    this.bool = bool;
  }

  public static final class SelfHolder {
    public static final PyBoolObject True = new PyBoolObject(true);
    public static final PyBoolObject False = new PyBoolObject(false);
  }

  public static PyBoolObject getInstance() {
    return SelfHolder.True;
  }

  public static PyBoolObject getTrue() {
    return SelfHolder.True;
  }

  public static PyBoolObject getFalse() {
    return SelfHolder.False;
  }

  public static PyBoolObject check(PyObject o) {
    return o == type ? getTrue() : getFalse();
  }

  public boolean isBool() {
    return bool;
  }

  public void setBool(boolean bool) {
    this.bool = bool;
  }

  @Override
  public String toString() {
    if (bool) return "True";
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
    if (!(o instanceof PyBoolObject)) return getFalse();
    return bool && ((PyBoolObject) o).bool ? getTrue() : getFalse();
  }

  @Override
  public PyObject xor(PyObject o) {
    if (!(o instanceof PyBoolObject)) return getFalse();
    return bool != ((PyBoolObject) o).bool ? getTrue() : getFalse();
  }

  @Override
  public PyObject or(PyObject o) {
    if (!(o instanceof PyBoolObject)) return getFalse();
    return bool || ((PyBoolObject) o).bool ? getTrue() : getFalse();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (obj instanceof PyBoolObject) return bool == ((PyBoolObject) obj).bool;
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
