package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeCall {

  /**
   * implementation of corresponding cpython tp_call
   *
   * @return {@link PyObject}
   */
  default PyObject call() throws PyNotImplemented {
    throw new PyNotImplemented("TypeCall call not implemented");
  }
}
