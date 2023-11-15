package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
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
