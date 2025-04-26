package org.jpvm.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.*;
import org.jpvm.objects.types.PyBaseObjectType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyTypeMethods;
import org.jpvm.python.BuiltIn;

/**
 * base class of all classes in python. all child classes in python are PyObject. Initialization
 * operations must be executed within the subclass themselves
 */
public class PyObject implements PyArgs, TypeCheck, TypeName, TypeStr, TypeRepr, TypeHash,
    TypeRichCompare, TypeInit, TypeCall, PyHashable, TypeGetMethod, TypeNew, TypeGetAttr,
    TypeGetAttro, TypeSetAttro, TypeSetAttr, PyTypeMethods {

  public static Operator[] compareOpMap;
  public static PyTypeType type;
  /** base class name of all classes in python */
  public static PyUnicodeObject name;
  /** parameterTypes of callable methods */
  public static Class<?>[] parameterTypes =
      new Class<?>[] {PyTupleObject.class, PyDictObject.class};

  static {
    compareOpMap = new Operator[PyCmp_BAD + 1];
    compareOpMap[Py_EQ] = Operator.Py_EQ;
    compareOpMap[Py_NE] = Operator.Py_NE;
    compareOpMap[Py_GT] = Operator.Py_GT;
    compareOpMap[Py_GE] = Operator.Py_GE;
    compareOpMap[Py_LE] = Operator.Py_LE;
    compareOpMap[Py_LT] = Operator.Py_LT;
    compareOpMap[PyCmp_IN] = Operator.PyCmp_IN;
    compareOpMap[PyCmp_NOT_IN] = Operator.PyCmp_NOT_IN;
    compareOpMap[PyCmp_IS] = Operator.PyCmp_IS;
    compareOpMap[PyCmp_IS_NOT] = Operator.PyCmp_IS_NOT;
    compareOpMap[PyCmp_EXC_MATCH] = Operator.PyCmp_EXC_MATCH;
    compareOpMap[PyCmp_BAD] = Operator.PyCmp_BAD;
    registerBaseObjectType();
  }

  protected PyDictObject dict;
  /** used to represent the type of PyObject instance */
  protected PyTypeType objType;

  protected PyLongObject hashCode;
  protected boolean hashDone;

  public PyObject() {}

  public static PyBoolObject check(PyObject o) {
    if (o == type)
      return BuiltIn.True;
    return BuiltIn.False;
  }

  public static synchronized void registerBaseObjectType() {
    if (type == null) {
      type = PyBaseObjectType.getInstance();
    }
  }

  public static synchronized void initBaseObject() {
    if (type == null)
      type = PyBaseObjectType.getInstance();
  }

  @Override
  public String toString() {
    return repr().toString();
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
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return getTypeName();
  }

  @Override
  @PyClassMethod
  public PyUnicodeObject __str__(PyTupleObject args, PyDictObject kwArgs) {
    return str();
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
    switch (op) {
      case Py_EQ, PyCmp_IS -> {
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
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
    return null;
  }

  public PyDictObject getDict() {
    return dict;
  }

  public synchronized void setDict(PyDictObject dict) {
    this.dict = dict;
  }

  @Override
  public int hashCode() {
    return (int) hash().getData();
  }

  @Override
  public PyObject getMethod(String name) throws PyException {
    return getMethod(new PyUnicodeObject(name));
  }

  @Override
  public PyObject getMethod(PyUnicodeObject name) throws PyException {
    PyObject function = getAttr(name);
    if (function instanceof PyMethodObject)
      return function;
    if (function instanceof PyFunctionObject) {
      return new PyMethodObject(this, (PyFunctionObject) function, name.getData());
    }
    return null;
  }

  /** be careful with call stack overflow if t == PyTypeType.type */
  protected PyObject lookUpType(PyObject key) throws PyException {
    PyTypeType t = (PyTypeType) getType();
    PyObject res;
    PyListObject mro = t.getMro();
    for (int i = 0; i < mro.size(); i++) {
      PyObject object = mro.get(i);
      if (object != PyTypeType.type) {
        res = object.getAttr(key);
        if (res != null)
          return res;
      }
    }
    return null;
  }

  protected PyObject getAttrInternal(PyObject key) throws PyException {
    PyObject descr = lookUpType(key);
    if (descr instanceof TypeDescriptorGet get && descr instanceof TypeDescriptorSet)
      return get.descrGet(this, getType());
    PyObject object = null;
    if (dict != null)
      object = dict.get(key);
    var name = (PyUnicodeObject) key;
    if (object == null) {
      if (descr instanceof PyFunctionObject func) {
        return func.descrGet(this, getType());
      }
      object = Utils.loadFiled(this, name);
    }
    if (object == null) {
      try {
        Method method = this.getClass().getMethod(name.getData(), PyObject.parameterTypes);
        if (method.isAnnotationPresent(PyClassMethod.class))
          object = new PyMethodObject(this, method, name.getData());
      } catch (NoSuchMethodException ignore) {
      }
    }
    if (object == null) {
      Class<?> clazz = this.getClass().getSuperclass();
      try {
        Field field = clazz.getDeclaredField(name.getData());
        if (field.isAnnotationPresent(PyClassAttribute.class)) {
          field.setAccessible(true);
          Object o = field.get(this);
          return (PyObject) o;
        }
      } catch (NoSuchFieldException | IllegalAccessException ignore) {
      }
    }
    return object;
  }

  @Override
  public PyObject getAttr(PyObject key) throws PyException {
    synchronized (this) {
      if (dict == null) {
        dict = new PyDictObject();
      }
    }
    PyObject res = dict.get(key);
    if (res != null)
      return res;
    PyObject attr = getAttrInternal(key);
    if (attr != null)
      dict.put(key, attr);
    return attr;
  }

  @Override
  public void setType(PyTypeType type) {
    objType = type;
  }

  @Override
  public PyObject getAttro(PyObject key) throws PyException {
    return getAttr(key);
  }

  @Override
  public PyObject setAttr(PyObject key, PyObject val) throws PyException {
    dict.put(key, val);
    return BuiltIn.None;
  }

  @Override
  public synchronized PyObject setAttro(PyObject key, PyObject val) throws PyException {
    return setAttr(key, val);
  }

  public synchronized void putAttribute(PyObject key, PyObject val) {
    try {
      dict.put(key, val);
    } catch (PyException ignored) {
    }
  }

  public PyObject getAttribute(PyObject key) {
    return dict.getOrDefault(key, BuiltIn.None);
  }

  @Override
  @PyClassMethod
  public PyObject __init__(PyTupleObject args, PyDictObject kwArgs) {
    return BuiltIn.None;
  }

  @Override
  @PyClassMethod
  public PyObject __new__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return this;
  }
}
