package org.jpvm.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
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
public class PyObject
    implements PyArgs,
        TypeCheck,
        TypeName,
        TypeStr,
        TypeRepr,
        TypeHash,
        TypeRichCompare,
        TypeInit,
        TypeCall,
        PyHashable,
        TypeGetMethod,
        TypeNew,
        TypeGetAttr,
        TypeGetAttro,
        TypeSetAttro,
        TypeSetAttr,
        PyTypeMethods {

  public static PyObject type;

  /** base class name of all classes in python */
  public static PyUnicodeObject name;
  /** parameterTypes of callable methods */
  public static Class<?>[] parameterTypes =
      new Class<?>[] {PyTupleObject.class, PyDictObject.class};

  protected PyDictObject dict;
  protected PyLongObject hashCode;
  protected boolean hashDone;

  public PyObject() {}

  public static PyBoolObject check(PyObject o) {
    if (o == type) return BuiltIn.True;
    return BuiltIn.False;
  }

  public static void registerBaseObjectType() {
    if (type == null) {
      type = new PyBaseObjectType();
    }
  }

  public static void initBaseObject() {
    if (type == null) type = new PyBaseObjectType();
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
    switch (op) {
      case Py_EQ, PyCmp_IS -> {
        if (o == this) return BuiltIn.True;
        return BuiltIn.False;
      }
      case PyCmp_IS_NOT -> {
        if (o != this) return BuiltIn.True;
        return BuiltIn.False;
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
    return null;
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
  public PyObject getMethod(String name) throws PyException {
    return getMethod(new PyUnicodeObject(name));
  }

  @Override
  public PyObject getMethod(PyUnicodeObject name) throws PyException {
    PyObject function = getAttr(name);
    if (function instanceof PyMethodObject) return function;
    if (function instanceof PyFunctionObject) {
      return new PyMethodObject(this, (PyFunctionObject) function, name.getData());
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError,
        getTypeName() + " " + this.repr().toString() + " has no method " + name.getData());
    return null;
  }

  /** be careful with call stack overflow if t == PyTypeType.type */
  protected PyObject lookUpType(PyObject key) throws PyException {
    PyTypeType t = (PyTypeType) getType();
    PyObject res;
    PyTupleObject mro = t.getMro();
    for (int i = 0; i < mro.size(); i++) {
      PyObject object = mro.get(i);
      if (object != PyTypeType.type) {
        res = object.getAttr(key);
        if (res != null) return res;
      }
    }
    return null;
  }

  @Override
  public PyObject getAttr(PyObject key) throws PyException {
    PyObject descr = lookUpType(key);
    if (descr instanceof TypeDescriptorGet get && descr instanceof TypeDescriptorSet)
      return get.descrGet(this, getType());
    PyObject object = null;
    if (dict != null) object = dict.get(key);
    var name = (PyUnicodeObject) key;
    if (object == null) {
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
    if (object == null) {
      try {
        Method method =
            this.getClass().getSuperclass().getMethod(name.getData(), PyObject.parameterTypes);
        if (method.isAnnotationPresent(PyClassMethod.class))
          object = new PyMethodObject(this, method, name.getData());
      } catch (NoSuchMethodException ignore) {
      }
    }
    // PyFunctionObject take priority over PyMethodObject
    if (descr instanceof PyFunctionObject func && object instanceof PyMethodObject) {
      return func;
    }
    if (object != null) return object;
    if (descr != null) {
      if (descr instanceof TypeDescriptorGet get) return get.descrGet(this, getType());
      return descr;
    }
    return null;
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
  public PyObject setAttro(PyObject key, PyObject val) throws PyException {
    return setAttr(key, val);
  }

  public void putAttribute(PyObject key, PyObject val) {
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
    return PyTypeMethods.super.__init__(args, kwArgs);
  }

  @Override
  @PyClassMethod
  public PyObject __new__(PyTupleObject args, PyDictObject kwArgs) {
    return PyTypeMethods.super.__new__(args, kwArgs);
  }
}
