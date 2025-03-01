package org.jpvm.stl.atomic;

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
  }
}
