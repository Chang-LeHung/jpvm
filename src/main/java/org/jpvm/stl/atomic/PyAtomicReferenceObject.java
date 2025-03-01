package org.jpvm.stl.atomic;

import java.util.concurrent.atomic.AtomicReference;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;

public class PyAtomicReferenceObject extends PyObject {

  private final AtomicReference<PyObject> value;

  public PyAtomicReferenceObject(PyObject value) {
    this.value = new AtomicReference<>(value);
  }

  @Override
  public PyObject getType() {
    return PyAtomicReferenceType.getInstance();
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return PyAtomicReferenceType.getInstance().getTypeName();
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

  @Override
  public PyUnicodeObject __str__(PyTupleObject args, PyDictObject kwArgs) {
   return value.get().__str__(args, kwArgs);
  }

  @Override
  public PyUnicodeObject __repr__(PyTupleObject args, PyDictObject kwArgs) {
    return value.get().__repr__(args, kwArgs);
  }
}
