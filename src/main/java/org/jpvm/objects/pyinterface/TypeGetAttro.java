package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeGetAttro {

  default PyObject getAttro(PyObject key) throws PyException {
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "TypeGetAttro getAttro not implemented");
  }
}
