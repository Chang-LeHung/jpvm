package org.jpvm.objects;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyLongType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.python.BuiltIn;

public class PyLongObject extends PyObject implements PyNumberMethods {

  public static final PyTypeType type = PyLongType.getInstance();

  public static PyLongObject[] miniPool = new PyLongObject[256 + 5];

  private long data;

  public PyLongObject(long data) {
    this.data = data;
  }

  public PyLongObject(int data) {
    this.data = data;
  }

  public PyLongObject() {
    this.data = 0;
  }

  public static PyBoolObject check(PyObject o) {
    return o == type ? BuiltIn.True : BuiltIn.False;
  }

  public static PyLongObject getLongObject(long num) {
    int idx = (int) num + 5;
    if (num <= 255 && num >= -5) {
      if (miniPool[idx] == null) {
        miniPool[idx] = new PyLongObject(num);
      }
      return miniPool[idx];
    } else {
      return new PyLongObject(num);
    }
  }

  public static PyLongObject getLongObject(int num) {
    return getLongObject((long) num);
  }

  @Override
  public String toString() {
    return String.valueOf(data);
  }

  public long getData() {
    return data;
  }

  public void setData(long data) {
    this.data = data;
  }

  public void fromString(String s) {
    data = Integer.parseInt(s);
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
    return new PyUnicodeObject(String.valueOf(data));
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyLongObject hash() {
    return new PyLongObject(this.data * 31);
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {

    if (o instanceof PyLongObject n) {
      switch (op) {
        case Py_GT -> {
          if (data > n.getData())
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case Py_EQ -> {
          if (data == n.getData())
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case Py_NE -> {
          if (data != n.getData())
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case Py_GE -> {
          if (data >= n.getData())
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case Py_LT -> {
          if (data < n.getData())
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case Py_LE -> {
          if (data <= n.getData())
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case PyCmp_IS -> {
          if (o == this)
            return BuiltIn.True;
          return BuiltIn.False;
        }
        case PyCmp_IS_NOT -> {
          if (o != this)
            return BuiltIn.True;
          return BuiltIn.False;
        }
      }
      return BuiltIn.False;
    }

    if (o instanceof TypeDoIterate itr) {
      switch (op) {
        case PyCmp_IN -> {
          while (itr.hasNext()) {
            if (itr.next().richCompare(this, Operator.Py_EQ).isTrue()) {
              return BuiltIn.True;
            }
          }
          return BuiltIn.False;
        }
        case PyCmp_NOT_IN -> {
          while (itr.hasNext()) {
            if (itr.next().richCompare(this, Operator.Py_EQ).isTrue()) {
              return BuiltIn.False;
            }
          }
          return BuiltIn.True;
        }
      }
    } else if (o instanceof TypeIterable itr) {
      TypeDoIterate iterate = itr.getIterator();
      switch (op) {
        case PyCmp_IN -> {
          while (iterate.hasNext()) {
            if (iterate.next().richCompare(this, Operator.Py_EQ).isTrue()) {
              return BuiltIn.True;
            }
          }
          return BuiltIn.False;
        }
        case PyCmp_NOT_IN -> {
          while (iterate.hasNext()) {
            if (iterate.next().richCompare(this, Operator.Py_EQ).isTrue()) {
              return BuiltIn.False;
            }
          }
          return BuiltIn.True;
        }
      }
    }

    if (op == Operator.PyCmp_IS) {
      return BuiltIn.False;
    } else if (op == Operator.PyCmp_IS_NOT) {
      return BuiltIn.True;
    }

    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "PyLongObject not support operator " + op);
    return null;
  }

  @Override
  public PyObject add(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply function add on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data + ((PyLongObject) o).getData());
  }

  @Override
  public PyObject sub(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply function sub on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data - ((PyLongObject) o).getData());
  }

  @Override
  public PyObject mul(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply function mul on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data * ((PyLongObject) o).getData());
  }

  @Override
  public PyObject mod(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply function mod on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data % ((PyLongObject) o).getData());
  }

  @Override
  public PyObject divmod(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply function divmod on int and " + o.getTypeName());
      return null;
    }
    PyTupleObject ret = new PyTupleObject(2);
    ret.set(0, trueDiv(o));
    ret.set(1, mod(o));
    return ret;
  }

  @Override
  public PyObject pow(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply function pow on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject((long) Math.pow(data, ((PyLongObject) o).getData()));
  }

  @Override
  public PyObject neg() {
    return new PyLongObject(-data);
  }

  @Override
  public PyObject pos() {
    return new PyLongObject(data);
  }

  @Override
  public PyObject abs() {
    return new PyLongObject(Math.abs(data));
  }

  @Override
  public PyObject bool() {
    return data != 0 ? BuiltIn.True : BuiltIn.False;
  }

  @Override
  public PyObject invert() {
    return new PyLongObject(~data);
  }

  @Override
  public PyObject lshift(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply << on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data << ((PyLongObject) o).getData());
  }

  @Override
  public PyObject rshift(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply >> on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data >> ((PyLongObject) o).getData());
  }

  @Override
  public PyObject and(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply & on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data & ((PyLongObject) o).getData());
  }

  @Override
  public PyObject xor(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply ^ on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data ^ ((PyLongObject) o).getData());
  }

  @Override
  public PyObject or(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "can apply or on int and " + o.getTypeName());
      return null;
    }
    return new PyLongObject(data | ((PyLongObject) o).getData());
  }

  @Override
  public PyObject nbInt() {
    return new PyLongObject(data);
  }

  @Override
  public PyObject nbFloat() {
    return new PyFloatObject(data);
  }

  @Override
  public PyObject floorDiv(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject))
      PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
          "can apply / on int and " + o.getTypeName());
    assert o instanceof PyLongObject;
    var d = (PyLongObject) o;
    if (d.getData() == 0) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.ZeroDivisionError, "division by zero");
    }
    return new PyLongObject(data / d.getData());
  }

  @Override
  public PyObject trueDiv(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject))
      PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
          "can apply / on int and " + o.getTypeName());
    assert o instanceof PyLongObject;
    var d = (PyLongObject) o;
    if (d.getData() == 0) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.ZeroDivisionError, "division by zero");
    }
    return new PyFloatObject((double) data / d.getData());
  }

  @Override
  public PyObject inplaceAdd(PyObject o) throws PyException {
    return add(o);
  }

  @Override
  public PyObject inplaceSub(PyObject o) throws PyException {
    return sub(o);
  }

  @Override
  public PyObject inplaceMul(PyObject o) throws PyException {
    return mul(o);
  }

  @Override
  public PyObject inplaceMod(PyObject o) throws PyException {
    return mod(o);
  }

  @Override
  public PyObject inplacePow(PyObject o) throws PyException {
    return pow(o);
  }

  @Override
  public PyObject inplaceLshift(PyObject o) throws PyException {
    return lshift(o);
  }

  @Override
  public PyObject inplaceRshift(PyObject o) throws PyException {
    return rshift(o);
  }

  @Override
  public PyObject inplaceAnd(PyObject o) throws PyException {
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

  @Override
  public PyObject inplaceFloorDiv(PyObject o) throws PyException {
    return floorDiv(o);
  }

  @Override
  public PyObject inplaceTrueDiv(PyObject o) throws PyException {
    return inplaceFloorDiv(o);
  }

  @Override
  public PyObject index() {
    return new PyLongObject(data);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (obj instanceof Integer o)
      return data == o;
    if (obj instanceof Long o)
      return data == o;
    return false;
  }
}
