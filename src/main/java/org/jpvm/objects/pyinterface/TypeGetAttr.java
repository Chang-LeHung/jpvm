package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeGetAttr {

  /**
   * implementation of corresponding cpython tp_getattr
   *
   * @return {@link PyObject}
   */
  default PyObject getAttr(PyObject key) throws PyException {
    throw new PyNotImplemented("TypeGetAttr getAttr not implemented");
  }

  @PyClassMethod
  default PyObject __getitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      return getAttr(args.get(0));
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, String.format("%s __getitem__ if and only if require 1 argument", this));
  }
}
