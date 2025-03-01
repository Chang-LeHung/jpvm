package org.jpvm.stl.atomic;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;

public class PyVolatileObject extends PyLongObject {
  private volatile PyObject value;

  public PyVolatileObject(PyObject value) {
    this.value = value;
  }

  @Override
  public PyObject getType() {
    return PyVolatileType.getInstance();
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return PyVolatileType.getInstance().getTypeName();
  }

  @PyClassMethod
  public PyObject load(PyTupleObject args, PyDictObject kwArgs) {
    return value;
  }

  @PyClassMethod
  public PyObject store(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 1)
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "store need one argument");

    var ret = value;
    value = args.get(0);
    return ret; // return old
  }
}
