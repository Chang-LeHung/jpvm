package org.jpvm.protocols;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface PyMappingMethods {

  /**
   * implementation of corresponding cpython mp_length
   */
  default PyObject mpLength(PyObject o){
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython mp_subscript
   */
  default PyObject mpSubscript(PyObject o){
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython mp_ass_subscript
   */
  default PyObject mpAssSubscript(PyObject o) {
    return Global.notImplemented;
  }
}
