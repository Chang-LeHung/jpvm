package org.jpvm.protocols;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface PySequenceMethods {

  /** implementation of corresponding cpython sq_length */
  default PyObject sqLength() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqLength not implemented");
  }

  @PyClassMethod
  default PyObject __len__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return sqLength();
  }

  /** implementation of corresponding cpython sq_concat */
  default PyObject sqConcat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqConcat not implemented");
  }

  @PyClassMethod
  default PyObject __add__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqConcat(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__add__ takes exactly one argument");
  }

  /** implementation of corresponding cpython sq_repeat */
  default PyObject sqRepeat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqRepeat not implemented");
  }

  @PyClassMethod
  default PyObject __mul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqRepeat(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "sqConcat takes exactly one argument");
  }

  /** implementation of corresponding cpython sq_item */
  default PyObject sqItem(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqItem not implemented");
  }

  @PyClassMethod
  default PyObject __getitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqItem(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__getitem__ takes exactly one argument");
  }

  /** implementation of corresponding cpython sq_ass_item */
  default PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "sqAssItem not implemented");
  }

  @PyClassMethod
  default PyObject __setitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2)
      return sqAssItem(args.get(0), args.get(1));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__setitem__ takes exactly two arguments");
  }

  @PyClassMethod
  default PyObject __delitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqAssItem(args.get(0), null);
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__delitem__ takes exactly two arguments");
  }

  /** implementation of corresponding cpython sq_contains */
  default PyObject sqContain(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "sqContain not implemented");
  }

  @PyClassMethod
  default PyObject __contains__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqConcat(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__contains__ takes exactly one argument");
  }

  /** implementation of corresponding cpython sq_inplace_concat */
  default PyObject sqInplaceConcat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "sqInplaceConcat not implemented");
  }

  @PyClassMethod
  default PyObject __iadd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqInplaceRepeat(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__iadd__ takes exactly one argument");
  }

  /** implementation of corresponding cpython sq_inplace_repeat */
  default PyObject sqInplaceRepeat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "sqInplaceRepeat not implemented");
  }

  @PyClassMethod
  default PyObject __imul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sqInplaceRepeat(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__imul__ takes exactly one argument");
  }
}
