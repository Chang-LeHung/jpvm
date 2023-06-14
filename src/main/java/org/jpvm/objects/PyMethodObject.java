package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.objects.types.PyMethodType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PyMethodObject extends PyObject {

  public static PyObject type = new PyMethodType();

  private final PyObject self;

  private final Method method;

  private final String methodName;

  public PyMethodObject(PyObject self, Method method, String methodName) {
    this.self = self;
    this.method = method;
    this.methodName = methodName;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public String toString() {
    return methodName;
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

  public PyObject getSelf() {
    return self;
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    try {
      return (PyObject) method.invoke(this.self, args, kwArgs);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new PyException(e.getCause().getMessage());
    }
  }
}
