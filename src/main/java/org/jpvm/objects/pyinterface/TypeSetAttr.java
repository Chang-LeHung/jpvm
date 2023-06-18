package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeSetAttr {

  /**
   * implementation of corresponding cpython tp_setattr
   *
   * @return {@link PyObject}
   */
  default PyObject setAttr(PyObject key, PyObject val) throws PyException {
    throw new PyNotImplemented("TypeSetAttr setAttr not implemented");
  }
}
