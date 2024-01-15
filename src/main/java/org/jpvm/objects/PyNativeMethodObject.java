package org.jpvm.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.types.PyNativeMethodType;
import org.jpvm.objects.types.PyTypeType;

public class PyNativeMethodObject extends PyObject {

  public static final PyTypeType type = PyNativeMethodType.getInstance();

  private final Method method;

  private final boolean isStatic;

  public PyNativeMethodObject(Method method, boolean isStatic) {
    this.method = method;
    this.isStatic = isStatic;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyObject self = args.get(0);
    PyTupleObject newArgs = new PyTupleObject(args.size() - 1);
    for (int i = 1; i < args.size(); i++) {
      newArgs.set(i - 1, args.get(i));
    }
    try {
      return (PyObject) method.invoke(self, newArgs, kwArgs);
    } catch (IllegalAccessException | InvocationTargetException e) {
      if (e.getCause() instanceof PyException) {
        throw (PyException) e.getCause();
      } else {
        System.out.println("All content below just for debugging");
        e.printStackTrace();
      }
      throw new PyException(e.getCause().getMessage());
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
