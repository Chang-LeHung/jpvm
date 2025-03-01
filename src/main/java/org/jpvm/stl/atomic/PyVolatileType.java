package org.jpvm.stl.atomic;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyVolatileType extends PyTypeType {

  private static final PyVolatileType selfHolder = new PyVolatileType(PyVolatileObject.class);

  private PyVolatileType(Class<?> clazz) {
    super(clazz);
  }

  public static PyVolatileType getInstance() {
    return selfHolder;
  }

  @Override
  public PyObject __call__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 1)
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__call__() takes exactly 1 argument (" +args.size() + " given)");
    return new PyVolatileObject(args.get(0));
  }
}
