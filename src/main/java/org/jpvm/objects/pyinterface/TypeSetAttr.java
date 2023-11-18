package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeSetAttr {

  /**
   * implementation of corresponding cpython tp_setattr
   *
   * @return {@link PyObject}
   */
  default PyObject setAttr(PyObject key, PyObject val) throws PyException {
    throw new PyNotImplemented("TypeSetAttr setAttr not implemented");
  }

  @PyClassMethod
  default PyObject __setitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      return setAttr(args.get(0), args.get(1));
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, String.format("%s __setitem__ if and only if require 2 argument", this));
  }
}
