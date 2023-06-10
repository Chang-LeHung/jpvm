package org.jpvm.objects;

import org.jpvm.errors.*;
import org.jpvm.objects.types.PyBytesType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

import java.util.Arrays;

public class PyBytesObject extends PyObject implements PyNumberMethods,
    PySequenceMethods, PyMappingMethods {

  public static PyObject type = new PyBytesType();
  private byte[] data;
  private PyLongObject hashCode;
  private boolean hashDone;

  public PyBytesObject(byte[] data) {
    this.data = data;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("b'");
    for (byte datum : data) {
      builder.append(Integer.toHexString((datum & 0xf0) >> 4));
      builder.append(Integer.toHexString(datum & 0xf));
    }
    builder.append("'");
    return builder.toString();
  }

  @Override
  public Object toJavaType() {
    return data;
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
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyLongObject hash() {
    if (!hashDone) {
      int h = 0;
      for (byte v : data) {
        h = 31 * h + (v & 0xff);
      }
      hashDone = true;
      hashCode = new PyLongObject(h);
      return hashCode;
    }
    return hashCode;
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (op == Operator.Py_EQ) {
      if (null == o)
        return BuiltIn.False;
      if (o instanceof PyBytesObject bytes) {
        if (Arrays.equals(data, bytes.data))
          return BuiltIn.True;
      }
      return BuiltIn.False;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
  }

  @Override
  public boolean equals(Object o) {
    if (null == o)
      return false;
    if (o instanceof PyBytesObject bytes) {
      return Arrays.equals(data, bytes.data);
    }
    return false;
  }

  @Override
  public PyBoolObject isHashable() {
    return BuiltIn.True;
  }

  @Override
  public PyObject mpLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(data.length);
  }

  @Override
  public PyObject mpSubscript(PyObject o) throws PyIndexOutOfBound, PyKeyError, PyTypeNotMatch, PyNotImplemented {
    return PyMappingMethods.super.mpSubscript(o);
  }

  @Override
  public PyObject mod(PyObject o) throws PyException {
    return PyNumberMethods.super.mod(o);
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(data.length);
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqConcat(o);
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqRepeat(o);
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqItem(o);
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyException {
    return PySequenceMethods.super.sqContain(o);
  }
}
