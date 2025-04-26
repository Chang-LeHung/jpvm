package org.jpvm.stl.atomic;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyAtomicReferenceType extends PyTypeType {

  private static final PyAtomicReferenceType type =
      new PyAtomicReferenceType(PyAtomicReferenceObject.class);

  private PyAtomicReferenceType(Class<?> clazz) {
    super(clazz);
  }

  public static PyAtomicReferenceType getInstance() {
    return type;
  }

  @Override
  public PyObject __new__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var o = args.get(0);
      return new PyAtomicReferenceObject(o);
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__new__ require 1 argument");
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return __new__(args, kwArgs);
  }
}
