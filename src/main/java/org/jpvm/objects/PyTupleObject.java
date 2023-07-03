package org.jpvm.objects;

import org.jpvm.errors.*;
import org.jpvm.internal.NumberHelper;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyTupleType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

/** tuple is a size-fixed array */
public class PyTupleObject extends PyObject
    implements TypeIterable, PySequenceMethods, PyNumberMethods {

  public static PyObject type = new PyTupleType();

  public static PyTupleObject zero = new PyTupleObject(0);

  private final PyObject[] obItem;

  public PyTupleObject(int size) {
    obItem = new PyObject[size];
  }

  public PyTupleObject(PyObject[] obItem) {
    this.obItem = obItem;
  }

  public static PyBoolObject check(PyObject o) {
    if (type == o) return BuiltIn.True;
    return BuiltIn.False;
  }

  public static PyObject getTupleBySize(int size) {
    if (size == 0) return zero;
    return new PyTupleObject(size);
  }

  public static PyTupleObject getTupleFromIterator(PyObject object) throws PyException {
    if (object instanceof TypeIterable iterable) {
      TypeDoIterate iterator = iterable.getIterator();
      PyTupleObject res = new PyTupleObject(iterator.size());
      int n = 0;
      while (iterator.hasNext()) {
        PyObject next = iterator.next();
        res.set(n++, next);
      }
      return res;
    } else if (object instanceof TypeDoIterate iterator) {
      PyTupleObject res = new PyTupleObject(iterator.size());
      int n = 0;
      while (iterator.hasNext()) {
        PyObject next = iterator.next();
        res.set(n++, next);
      }
      return res;
    }
    throw new PyException("getTupleFromIterator require TypeIterable or Iterator");
  }

  public void set(int idx, PyObject obj) {
    obItem[idx] = obj;
  }

  public PyObject get(int idx) {
    if (idx >= obItem.length)
      throw new IndexOutOfBoundsException(
          "idx = " + idx + " out of PyTupleObject bound with size = " + obItem.length);
    return obItem[idx];
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("(");
    for (PyObject object : obItem) {
      builder.append(object.repr());
      builder.append(", ");
    }
    if (builder.length() > 2) builder.delete(builder.length() - 2, builder.length());
    builder.append(")");
    return builder.toString();
  }

  @Override
  public PyObject mul(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (o instanceof PyLongObject pyLongObject) {
      PyTupleObject o1 = new PyTupleObject((int) (size() * pyLongObject.getData()));
      for (int i = 0; i < pyLongObject.getData(); i++) {
        for (int j = 0; j < size(); j++) {
          o1.obItem[i * size() + j] = obItem[j];
        }
      }
      return o1;
    }
    throw new PyTypeNotMatch("require PyTupleObject type");
  }

  @Override
  public PyObject add(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return sqConcat(o);
  }

  @Override
  public Object toJavaType() {
    return obItem;
  }

  public int size() {
    return obItem.length;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyBoolObject isHashable() {
    return BuiltIn.True;
  }

  @Override
  public TypeDoIterate getIterator() {
    return new PyTupleItrObject();
  }

  @Override
  public PyLongObject hash() {
    if (hashDone) return hashCode;
    int h = 0;
    for (PyObject e : obItem) {
      h = 31 * h + (e == null ? 0 : (int) e.hash().getData());
    }
    hashDone = true;
    hashCode = new PyLongObject(h);
    return hashCode;
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (!(o instanceof PyTupleObject tuple)) {
      throw new PyTypeNotMatch("can only support PyTupleObject");
    }
    switch (op) {
      case Py_EQ -> {
        if (tuple.size() != size()) {
          return BuiltIn.False;
        }
        for (int i = 0; i < size(); i++) {
          if (obItem[i] != tuple.obItem[i]) return BuiltIn.False;
        }
        return BuiltIn.True;
      }
      case PyCmp_IN -> {
        for (PyObject pyObject : obItem) {
          if (pyObject.richCompare(o, Operator.Py_EQ).isTrue()) return BuiltIn.True;
        }
        return BuiltIn.False;
      }
      case PyCmp_NOT_IN -> {
        for (PyObject pyObject : obItem) {
          if (pyObject.richCompare(o, Operator.Py_NE).isTrue()) return BuiltIn.True;
        }
        return BuiltIn.False;
      }
      case Py_LT -> {
        int llen = size();
        int rlen = tuple.size();
        int i = 0, j = 0;
        for (; i < llen && j < rlen; i++, j++) {
          if (this.get(i).richCompare(tuple.get(j), Operator.Py_EQ).isTrue()) {
          } else if (this.get(i).richCompare(tuple.get(j), Operator.Py_LT).isTrue()) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
        if (llen < rlen) {
          return BuiltIn.True;
        } else {
          return BuiltIn.False;
        }
      }
      case Py_GT -> {
        int llen = size();
        int rlen = tuple.size();
        int i = 0, j = 0;
        for (; i < llen && j < rlen; i++, j++) {
          if (this.get(i).richCompare(tuple.get(j), Operator.Py_EQ).isTrue()) {
          } else if (this.get(i).richCompare(tuple.get(j), Operator.Py_GT).isTrue()) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
        if (llen > rlen) {
          return BuiltIn.True;
        } else {
          return BuiltIn.False;
        }
      }
      case Py_LE -> {
        int llen = size();
        int rlen = tuple.size();
        int i = 0, j = 0;
        for (; i < llen && j < rlen; i++, j++) {
          if (this.get(i).richCompare(tuple.get(j), Operator.Py_EQ).isTrue()
              || this.get(i).richCompare(tuple.get(j), Operator.Py_LT).isTrue()) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
        if (llen < rlen) {
          return BuiltIn.True;
        } else {
          return BuiltIn.False;
        }
      }
      case Py_GE -> {
        int llen = size();
        int rlen = tuple.size();
        int i = 0, j = 0;
        for (; i < llen && j < rlen; i++, j++) {
          if (this.get(i).richCompare(tuple.get(j), Operator.Py_EQ).isTrue()
              || this.get(i).richCompare(tuple.get(j), Operator.Py_GE).isTrue()) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
        if (llen > rlen) {
          return BuiltIn.True;
        } else {
          return BuiltIn.False;
        }
      }
    }
    throw new PyTypeNotMatch("can only support PyTupleObject");
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(obItem.length);
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    if (o instanceof PyTupleObject tupleObject) {
      PyTupleObject o1 = new PyTupleObject(size() + tupleObject.size());
      for (int i = 0; i < o1.size(); i++) {
        if (i < obItem.length) {
          o1.obItem[i] = obItem[i];
        } else {
          o1.obItem[i] = tupleObject.obItem[i - size()];
        }
      }
      return o1;
    }
    throw new PyTypeNotMatch("require PyTupleObject type");
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqRepeat(o);
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    if (o instanceof PyLongObject) {
      Long n = NumberHelper.transformPyObject2Long(o);
      if (n == null) throw new PyTypeNotMatch("require PyNumberMethods type");
      return get(n.intValue());
    } else if (o instanceof PySliceObject slice) {
      PyListObject idx = slice.unpacked(this);
      PyTupleObject result = new PyTupleObject(idx.size());
      for (int i = 0; i < idx.size(); i++) {
        int index = (int) ((PyLongObject) idx.get(i)).getData();
        result.set(i, get(index));
      }
      return result;
    }
    throw new PyTypeNotMatch("require PyNumberMethods type");
  }

  /** only use @PyClassMethod annotation could be called in python source code */
  @PyClassMethod
  public PyObject index(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject o = args.get(0);
      for (int i = 0; i < size(); i++) {
        if (get(i).richCompare(o, Operator.Py_EQ).isTrue()) {
          return PyLongObject.getLongObject(i);
        }
      }
      return PyLongObject.getLongObject(-1);
    }
    throw new PyTypeNotMatch("PyTupleObject method index only require one argument");
  }

  @PyClassMethod
  public PyObject count(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      int count = 0;
      for (int i = 0; i < size(); i++) {
        if (obItem[i].richCompare(args.get(0), Operator.Py_EQ).isTrue()) {
          count++;
        }
      }
      return PyLongObject.getLongObject(count);
    }
    throw new PyTypeNotMatch("PyTupleObject method count only require one argument");
  }

  @Override
  public PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    return PySequenceMethods.super.sqAssItem(key, val);
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyException {
    return PySequenceMethods.super.sqContain(o);
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
    return str();
  }

  public static class PyTupleItrType extends PyTypeType {
    public PyTupleItrType() {
      super(PyTupleItrObject.class);
      name = "tuple_iterator";
    }
  }

  public class PyTupleItrObject extends PyObject implements TypeDoIterate {
    public static PyObject type = new PyTupleItrType();
    private int idx;

    public PyTupleItrObject() {
      idx = 0;
    }

    @Override
    public PyObject next() throws PyException {
      if (idx < obItem.length) return obItem[idx++];
      return BuiltIn.PyExcStopIteration;
    }

    @Override
    public PyObject get(int idx) throws PyIndexOutOfBound, PyNotImplemented {
      if (idx < obItem.length) return obItem[idx];
      throw new PyIndexOutOfBound("tuple is out of bound for index " + idx);
    }

    @Override
    public int size() {
      return obItem.length;
    }

    @Override
    public boolean hasNext() {
      return idx < size();
    }
  }
}
