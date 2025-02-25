package org.jpvm.stl.atomic;

import java.util.concurrent.atomic.AtomicLong;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.types.PyTypeType;

public class PyAtomicLongObject extends PyObject {

  private final AtomicLong value;

  public static final PyTypeType type = PyAtomicLongType.getInstance();

  public PyAtomicLongObject(long value) {
    this.value = new AtomicLong(value);
  }

  public PyAtomicLongObject() {
    this.value = new AtomicLong();
  }

  @PyClassMethod
  public PyLongObject get(PyTupleObject args, PyDictObject kwArgs) {
    return PyLongObject.getLongObject(value.get());
  }

  @PyClassMethod
  public PyObject atomic_add(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var o = args.get(0);
      if (o instanceof PyLongObject val) {
        value.addAndGet(val.getData());
        return this;
      }
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "atomic_add require an int argument");
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "atomic_add require 1 argument");
  }

  @PyClassMethod
  public PyObject atomic_sub(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var o = args.get(0);
      if (o instanceof PyLongObject val) {
        value.addAndGet(-val.getData());
        return this;
      }
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "atomic_sub require an int argument");
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "atomic_sub require 1 argument");
  }

  @PyClassMethod
  public PyObject atomic_mul(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var o = args.get(0);
      if (o instanceof PyLongObject val) {
        do {
          long old = value.get();
          if (value.compareAndSet(old, old * val.getData())) {
            break;
          }
        } while (true);
        return this;
      }
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "atomic_mul require an int argument");
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "atomic_mul require 1 argument");
  }

  @PyClassMethod
  public PyObject atomic_div(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var o = args.get(0);
      if (o instanceof PyLongObject val) {
        do {
          long old = value.get();
          if (value.compareAndSet(old, old / val.getData())) {
            break;
          }
        } while (true);
        return this;
      }
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "atomic_div require an int argument");
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "atomic_div require 1 argument");
  }

  @Override
  public PyObject getType() {
    return PyAtomicLongType.getInstance();
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return PyAtomicLongType.getInstance().getTypeName();
  }
}
