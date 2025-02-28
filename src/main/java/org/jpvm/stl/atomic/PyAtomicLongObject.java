package org.jpvm.stl.atomic;

import java.util.concurrent.atomic.AtomicLong;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;

public class PyAtomicLongObject extends PyObject implements PyNumberMethods {

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

  @Override
  public PyUnicodeObject __str__(PyTupleObject args, PyDictObject kwArgs) {
   return new PyUnicodeObject(String.valueOf(value.get()));
  }

  @Override
  public PyUnicodeObject __repr__(PyTupleObject args, PyDictObject kwArgs) {
    return new PyUnicodeObject(String.valueOf(value.get()));
  }

  @Override
  public PyObject __iadd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return atomic_add(args, kwArgs);
  }

  @Override
  public PyObject __isub__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return atomic_sub(args, kwArgs);
  }

  @Override
  public PyObject __imul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return atomic_mul(args, kwArgs);
  }

  @Override
  public PyObject inplaceFloorDiv(PyObject o) throws PyException {
    return atomic_div(new PyTupleObject(new PyObject[]{o}), null);
  }

  @Override
  public PyObject inplaceAdd(PyObject o) throws PyException {
    return __iadd__(new PyTupleObject(new PyObject[]{o}), null);
  }

  @Override
  public PyObject inplaceSub(PyObject o) throws PyException {
    return atomic_sub(new PyTupleObject(new PyObject[]{o}), null);
  }

  @Override
  public PyObject inplaceMul(PyObject o) throws PyException {
    return atomic_mul(new PyTupleObject(new PyObject[]{o}), null);
  }
}
