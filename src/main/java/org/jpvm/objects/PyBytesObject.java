package org.jpvm.objects;

import java.util.Arrays;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.objects.types.PyBytesType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

public class PyBytesObject extends PyObject
    implements PyNumberMethods, PySequenceMethods, PyMappingMethods {

  public static final PyTypeType type = PyBytesType.getInstance();
  private byte[] data;
  private final int size;
  private PyLongObject hashCode;
  private boolean hashDone;

  public PyBytesObject(byte[] data) {
    this.data = data;
    size = data.length;
  }

  public PyBytesObject(byte[] data, int size) {
    this.data = data;
    this.size = size;
  }

  public byte[] getData() {
    return data;
  }

  public synchronized void setData(byte[] data) {
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
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (op != Operator.Py_EQ) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
      return null;
    }
    if (null == o) return BuiltIn.False;
    if (o instanceof PyBytesObject bytes) {
      if (Arrays.equals(data, bytes.data)) return BuiltIn.True;
    }
    return BuiltIn.False;
  }

  @Override
  public boolean equals(Object o) {
    if (null == o) return false;
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
  public PyObject mpLength() throws PyNotImplemented {
    return new PyLongObject(data.length);
  }

  @Override
  public PyObject mpSubscript(PyObject o) throws PyException {
    return PyMappingMethods.super.mpSubscript(o);
  }

  @Override
  public PyObject __add__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyNumberMethods.super.__add__(args, kwArgs);
  }

  @Override
  public PyObject __mul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyNumberMethods.super.__mul__(args, kwArgs);
  }

  @Override
  public PyObject mod(PyObject o) throws PyException {
    return PyNumberMethods.super.mod(o);
  }

  @Override
  public PyObject sqLength() throws PyNotImplemented {
    return new PyLongObject(data.length);
  }

  @Override
  public PyObject __len__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PySequenceMethods.super.__len__(args, kwArgs);
  }

  @Override
  public synchronized PyObject sqConcat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqConcat(o);
  }

  @Override
  public synchronized PyObject sqRepeat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqRepeat(o);
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyException {
    return PySequenceMethods.super.sqItem(o);
  }

  @Override
  public PyObject __getitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PySequenceMethods.super.__getitem__(args, kwArgs);
  }

  @Override
  public PyObject __setitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PySequenceMethods.super.__setitem__(args, kwArgs);
  }

  @Override
  public PyObject __delitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PySequenceMethods.super.__delitem__(args, kwArgs);
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyException {
    return PySequenceMethods.super.sqContain(o);
  }

  @Override
  public PyObject bool() throws PyException {
    if (size == 0) return BuiltIn.False;
    return BuiltIn.True;
  }

  @Override
  public PyObject __iadd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyNumberMethods.super.__iadd__(args, kwArgs);
  }

  @Override
  public PyObject __imul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyNumberMethods.super.__imul__(args, kwArgs);
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return super.call(args, kwArgs);
  }
}
