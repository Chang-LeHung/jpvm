package org.jpvm.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jpvm.errors.PyException;
import org.jpvm.objects.types.PyMethodType;
import org.jpvm.pvm.Abstract;

public class PyMethodObject extends PyObject {

  public static PyObject type = new PyMethodType();

  private PyObject self;
  private final String methodName;
  private Method method;
  private PyFunctionObject functionObject;

  public PyMethodObject(PyObject self, Method method, String methodName) {
    this.self = self;
    this.method = method;
    this.methodName = methodName;
  }

  public PyMethodObject(Method method, String methodName) {
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

  public void setSelf(PyObject self) {
    this.self = self;
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
        if (null != this.self) return (PyObject) method.invoke(this.self, args, kwArgs);
        else {
          self = args.get(0);
          PyTupleObject eliArgs = new PyTupleObject(args.size() - 1);
          for (int i = 1; i < args.size(); i++) {
            eliArgs.set(i - 1, args.get(i));
          }
          return (PyObject) method.invoke(self, eliArgs, kwArgs);
        }
      } else {
        // pass self to class function
        args = Utils.packSelfAsTuple(this.self, args);
        return Abstract.abstractCall(functionObject, null, args, kwArgs);
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new PyException(e.getCause().getMessage());
    }
  }
}
