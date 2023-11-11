package org.jpvm.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.types.PyNativeMethodType;

public class PyNativeMethodObject extends PyObject {

  public static PyObject type = new PyNativeMethodType();

  private final Method method;

  private final boolean isStatic;

  public PyNativeMethodObject(Method method, boolean isStatic) {
    this.method = method;
    this.isStatic = isStatic;
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    try {
      return (PyObject) method.invoke(self, args, kwArgs);
    } catch (IllegalAccessException | InvocationTargetException e) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getCause().getMessage());
      return null;
    }
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
