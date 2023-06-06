package org.jpvm.objects.types;

import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeNew;
import org.jpvm.python.BuiltIn;

import java.util.ArrayList;
import java.util.List;

public class PyTypeType extends PyObject implements TypeNew {

  /* Objects behave like an unbound method */
  public static int Py_TPFLAGS_METHOD_DESCRIPTOR = (1 << 17);

  /* Objects support type attribute cache */

  public static int Py_TPFLAGS_HAVE_VERSION_TAG = (1 << 18);

  public static int Py_TPFLAGS_VALID_VERSION_TAG = (1 << 19);

  /* Type is abstract and cannot be instantiated */
  public static int Py_TPFLAGS_IS_ABSTRACT = (1 << 20);

  /* These flags are used to determine if a type is a subclass. */
  public static int Py_TPFLAGS_LONG_SUBCLASS = (1 << 24);
  public static int Py_TPFLAGS_LIST_SUBCLASS = (1 << 25);
  public static int Py_TPFLAGS_TUPLE_SUBCLASS = (1 << 26);
  public static int Py_TPFLAGS_BYTES_SUBCLASS = (1 << 27);
  public static int Py_TPFLAGS_UNICODE_SUBCLASS = (1 << 28);
  public static int Py_TPFLAGS_DICT_SUBCLASS = (1 << 29);
  public static int Py_TPFLAGS_BASE_EXC_SUBCLASS = (1 << 30);
  public static int Py_TPFLAGS_TYPE_SUBCLASS = (1 << 31);

  public static PyObject type = new PyTypeType();

  protected String name;

  public PyTypeType() {
    name = "type";
    // use List just to avoid ExceptionInInitializerError
    mro = new ArrayList<>();
    mro.add(PyObject.type);
  }

  /**
   * this object is subtype of r or not
   */
  public PyBoolObject isSubType(PyTypeType r) {
    for (int i = 0; i < mro.size(); i++) {
      PyObject pyObject = mro.get(i);
      if (pyObject == r)
        return BuiltIn.True;
    }
    return BuiltIn.False;
  }

  public PyTupleObject getMro() {
    PyTupleObject object = new PyTupleObject(mro.size());
    for (int i = 0; i < mro.size(); i++) {
      object.set(i, mro.get(i));
    }
    return object;
  }

  public void setMro(List<PyObject> mro) {
    this.mro = mro;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return PyUnicodeObject.getOrCreateFromInternStringPool(name, true);
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(String.format("<class '%s'>", name));
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (Operator.Py_EQ == op) {
      if (this == o) return BuiltIn.True;
      else
        return BuiltIn.False;
    }
    throw new PyUnsupportedOperator(getTypeName() + " not support operator " + op);
  }
}
