package org.jpvm.objects;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.types.PyMethodType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PyMethodObject extends PyObject {

  public static PyObject type = new PyMethodType();

  private final PyObject self;

  private final PyUnicodeObject methodName;

  public PyMethodObject(PyObject self, PyUnicodeObject methodName) {
    this.self = self;
    this.methodName = methodName;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public String toString() {
    return methodName.toString();
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

  public PyObject execute(Object... args) throws PyNotImplemented {
    Class<? extends PyObject> clazz = self.getClass();
    try {
      Method method = clazz.getMethod((String) methodName.toJavaType());
      return (PyObject) method.invoke(self, args);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new PyNotImplemented("");
    }
  }
}
