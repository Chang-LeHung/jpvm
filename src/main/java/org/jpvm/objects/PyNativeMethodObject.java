package org.jpvm.objects;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.types.PyNativeMethodType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PyNativeMethodObject extends PyObject {

  public static PyObject type = new PyNativeMethodType();

  private final Method method;

  private final boolean isStatic;

  public PyNativeMethodObject(Method method, boolean isStatic) {
    this.method = method;
    this.isStatic = isStatic;
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyNotImplemented {
    try {
      return (PyObject) method.invoke(self, args, kwArgs);
    } catch (IllegalAccessException | InvocationTargetException ignore) {
    }
    return null;
  }

  public boolean isStatic() {
    return isStatic;
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
    return type.str();
  }

  @Override
  public PyUnicodeObject repr() {
    return type.repr();
  }
}
