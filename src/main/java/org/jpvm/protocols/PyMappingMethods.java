package org.jpvm.protocols;

import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface PyMappingMethods {

  /** implementation of corresponding cpython mp_length */
  default PyObject mpLength(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "mpLength is not implemented");
    return null;
  }

  /** implementation of corresponding cpython mp_subscript */
  default PyObject mpSubscript(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "mpSubscript is not implemented");
    return null;
  }

  /** implementation of corresponding cpython mp_ass_subscript */
  default PyObject mpAssSubscript(PyObject key, PyObject val) throws PyException {
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "mpAssSubscript is not implemented");
    return null;
  }
}
