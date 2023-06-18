package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.objects.types.PyMethodType;
import org.jpvm.pvm.Abstract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PyMethodObject extends PyObject {

  public static PyObject type = new PyMethodType();

  private final PyObject self;

  private Method method;

  private PyFunctionObject functionObject;

  private final String methodName;

  public PyMethodObject(PyObject self, Method method, String methodName) {
    this.self = self;
    this.method = method;
    this.methodName = methodName;
  }

  public PyMethodObject(PyObject self, PyFunctionObject functionObject, String methodName) {
    this.self = self;
    this.functionObject = functionObject;
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
      if (method != null) {
        return (PyObject) method.invoke(this.self, args, kwArgs);
      }else {
        // pass self to class function
        args = Utils.packSelfAsTuple(this.self, args);
        return Abstract.abstractCall(functionObject, null, args, kwArgs);
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new PyException(e.getCause().getMessage());
    }
  }
}
