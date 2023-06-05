package org.jpvm.objects;


import org.jpvm.errors.PyMissMethod;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.pyinterface.*;
import org.jpvm.objects.types.PyBaseObjectType;
import org.jpvm.python.BuiltIn;

import java.lang.reflect.Method;

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
  protected PyListObject mro;
  protected PyListObject bases;
  private PyDictObject dict;
  private PyLongObject hashcode;

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
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
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

  @Override
  public PyObject init() throws PyNotImplemented {
    type = new PyBaseObjectType();
    return this;
  }

  @Override
  public PyObject getMethod(String name) throws PyMissMethod {
    PyUnicodeObject object = new PyUnicodeObject(name);
    if (dict == null) {
      dict = new PyDictObject();
      return getMethodByName(name, object);
    } else {
      if (dict.get(object) != null) {
        return dict.get(object);
      } else {
        return getMethodByName(name, object);
      }
    }
  }

  @Override
  public PyObject getMethod(PyUnicodeObject name) throws PyMissMethod {
    return getMethod(name.getData());
  }

  private PyObject getMethodByName(String name, PyUnicodeObject object) throws PyMissMethod {
    Class<? extends PyObject> clazz = this.getClass();
    try {
      Method method = clazz.getMethod(name);
      PyMethodObject m = new PyMethodObject(this, method, name);
      dict.put(object, m);
      return m;
    } catch (NoSuchMethodException e) {
      throw new PyMissMethod(this.repr().getData() + " has not method " + name);
    }
  }

}
