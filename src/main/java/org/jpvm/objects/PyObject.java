package org.jpvm.objects;


import org.jpvm.errors.PyException;
import org.jpvm.errors.PyMissMethod;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.pyinterface.*;
import org.jpvm.objects.types.PyBaseObjectType;
import org.jpvm.python.BuiltIn;

import java.util.List;

/**
 * base class of all classes in python
 */
public class PyObject implements PyArgs, TypeCheck,
    TypeName, TypeStr, TypeRepr, TypeHash, TypeRichCompare,
    TypeInit, TypeCall, PyHashable, TypeGetMethod {

  public static PyObject type;

  /**
   * base class name of all classes in python
   */
  public static PyUnicodeObject name;
  /**
   * parameterTypes of callable methods
   */
  public static Class<?>[] parameterTypes = new Class<?>[]{PyTupleObject.class, PyDictObject.class};
  protected List<PyObject> mro;
  protected PyListObject bases;
  protected PyDictObject dict;
  protected PyLongObject hashCode;
  protected boolean hashDone;

  public static PyBoolObject check(PyObject o) {
    if (o == type)
      return BuiltIn.True;
    return BuiltIn.False;
  }

  public static void registerBaseObjectType() {
    if (type == null) {
      type = new PyBaseObjectType();
    }
  }

  @Override
  public String toString() {
    return "<PyObject>";
  }

  @Override
  public Object toJavaType() {
    return null;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    if (name == null) {
      name = new PyUnicodeObject("object");
    }
    return name;
  }

  @Override
  public PyUnicodeObject str() {
    return getTypeName();
  }

  @Override
  public PyUnicodeObject repr() {
    return getTypeName();
  }

  @Override
  public PyLongObject hash() {
    return new PyLongObject(0);
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (op == Operator.Py_EQ) {
      if (o == this)
        return BuiltIn.True;
      return BuiltIn.False;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
  }

  public PyDictObject getDict() {
    return dict;
  }

  public void setDict(PyDictObject dict) {
    this.dict = dict;
  }

  @Override
  public int hashCode() {
    return (int) hash().getData();
  }

  /**
   * initialize methods object
   */
  @Override
  public PyObject init() throws PyNotImplemented {
    type = new PyBaseObjectType();
    return this;
  }

  @Override
  public PyObject getMethod(String name) throws PyMissMethod {
    return getMethod(new PyUnicodeObject(name));
  }

  @Override
  public PyObject getMethod(PyUnicodeObject name) throws PyMissMethod {
    return null;
//    PyObject method = methods.get(name);
//    if (method == null)
//      throw new PyMissMethod("not have method" + name);
//    return method;
  }

}
