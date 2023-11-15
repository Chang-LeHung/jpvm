package org.jpvm.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.types.PyMethodType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.vm.Abstract;

public class PyMethodObject extends PyObject {

  public static final PyTypeType type = new PyMethodType();
  private final String methodName;
  private PyObject self;
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
    String builder = "<PyMethodObject " + method.getName() + " >";
    return new PyUnicodeObject(builder);
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  public PyObject getSelf() {
    return self;
  }

  public void setSelf(PyObject self) {
    this.self = self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyObject self;
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
      if (e.getCause() instanceof PyException) throw (PyException) e.getCause();
      PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getCause().getMessage());
      return null;
    }
  }

  public Method getNativeMethod() {
    return method;
  }
}
