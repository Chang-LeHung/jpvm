package org.jpvm.objects.pyinterface;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.jobjs.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeGetAttr {

  /**
   * implementation of corresponding cpython tp_getattr
   *
   * @return {@link PyObject}
   */
  default PyObject getAttr(PyObject key) throws PyException {
    throw new PyNotImplemented("TypeGetAttr getAttr not implemented");
  }
}
