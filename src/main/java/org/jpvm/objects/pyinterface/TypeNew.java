package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeNew {

  /**
   * implementation of corresponding cpython tp_new
   *
   * @return {@link PyObject}
   */
  default PyObject allocate(PyObject args, PyObject kwArgs) throws PyException {
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "TypeNew allocate not implemented");
  }
}
