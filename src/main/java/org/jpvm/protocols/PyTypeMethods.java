package org.jpvm.protocols;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.python.BuiltIn;

public interface PyTypeMethods {

  default PyObject __init__(PyTupleObject args, PyDictObject kwArgs) {
    return BuiltIn.None;
  }

  default PyObject __new__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "");
  }
}
