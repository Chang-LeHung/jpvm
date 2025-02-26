package org.jpvm.stl.atomic;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyAtomicLongType extends PyTypeType {

  private static final PyAtomicLongType self = new PyAtomicLongType(PyAtomicLongObject.class);

  private PyAtomicLongType(Class<?> clazz) {
    super(clazz);
  }

  public static PyAtomicLongType getInstance() {
    return self;
  }

  @Override
  public PyObject __new__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0)
      return new PyAtomicLongObject();
    if (args.size() == 1) {
      var o = args.get(0);
      if (o instanceof PyLongObject val) {
        return new PyAtomicLongObject(val.getData());
      }
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__new__ require an int argument");
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__new__ require 0 or 1 argument");
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return __new__(args, kwArgs);
  }
}
