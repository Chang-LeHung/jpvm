package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeInit {

  /**
   * implementation of corresponding cpython tp_init
   * @return {@link PyObject}
   */
  default PyObject init() throws PyNotImplemented {
    throw new PyNotImplemented("TypeInit init not implemented");
  }
}
