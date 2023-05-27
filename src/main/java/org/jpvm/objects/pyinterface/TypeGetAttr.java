package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeGetAttr {

  /**
   * implementation of corresponding cpython tp_getattr
   * @return {@link PyObject}
   */
  default PyObject getAttr(PyObject key) throws PyNotImplemented {
    throw new PyNotImplemented("TypeGetAttr getAttr not implemented");
  }
}
