package org.jpvm.protocols;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface PyMappingMethods {

  /** implementation of corresponding cpython mp_length */
  default PyObject mpLength() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "mpLength is not implemented");
  }

  @PyClassMethod
  default PyObject __len__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return mpLength();
  }

  /** implementation of corresponding cpython mp_subscript */
  default PyObject mpSubscript(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "mpSubscript is not implemented");
  }

  @PyClassMethod
  default PyObject __getitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return mpSubscript(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__getitem__ takes exactly one argument");
  }

  /** implementation of corresponding cpython mp_ass_subscript */
  default PyObject mpAssSubscript(PyObject key, PyObject val) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "mpAssSubscript is not implemented");
  }

  @PyClassMethod
  default PyObject __setitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2)
      return mpAssSubscript(args.get(0), args.get(1));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__setitem__ takes exactly two arguments");
  }

  @PyClassMethod
  default PyObject __delitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return mpAssSubscript(args.get(0), null);
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__delitem__ takes exactly two arguments");
  }
}
