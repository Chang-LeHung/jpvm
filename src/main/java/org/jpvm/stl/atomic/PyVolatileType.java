package org.jpvm.stl.atomic;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyVolatileType extends PyTypeType {

  private static final PyVolatileType type = new PyVolatileType(PyVolatileObject.class);

  private PyVolatileType(Class<?> clazz) {
    super(clazz);
  }

  public static PyVolatileType getInstance() {
    return type;
  }

  @Override
  public PyObject __new__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var o = args.get(0);
      return new PyVolatileObject(o);
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__new__ require 1 argument");
  }
}
