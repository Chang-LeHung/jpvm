package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeSetAttro {

  default PyObject setAttro(PyObject key, PyObject val) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "TypeSetAttro setAttro not implemented");
  }
}
