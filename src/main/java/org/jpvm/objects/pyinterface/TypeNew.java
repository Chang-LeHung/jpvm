package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeNew {

  /**
   * implementation of corresponding cpython tp_new
   *
   * @return {@link PyObject}
   */
  default PyObject allocate(PyObject args, PyObject kwArgs) throws PyNotImplemented {
    throw new PyNotImplemented("TypeNew allocate not implemented");
  }
}
