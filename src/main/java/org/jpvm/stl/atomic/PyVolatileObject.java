package org.jpvm.stl.atomic;

import java.util.concurrent.atomic.AtomicReference;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;

public class PyVolatileObject extends PyObject {

  private final AtomicReference<PyObject> value;

  public PyVolatileObject(PyObject value) {
    this.value = new AtomicReference<>(value);
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
  public PyObject get(PyTupleObject args, PyDictObject kwArgs) {
    return value.get();
  }

  @PyClassMethod
  public PyObject set(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 1) throw new RuntimeException("set() takes exactly one argument (0 given)");
    value.set(args.get(0));
    return this;
  }

  @PyClassMethod
  public PyObject compareAndSwap(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 2) throw new RuntimeException("compareAndSwap() takes exactly two argument (0 given)");
    PyObject old = args.get(0);
    PyObject newValue = args.get(1);
    if (value.compareAndSet(old, newValue))
      return PyBoolObject.getTrue();
    return PyBoolObject.getFalse();
  }
}
