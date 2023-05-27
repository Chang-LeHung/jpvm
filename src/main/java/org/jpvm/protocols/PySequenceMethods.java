package org.jpvm.protocols;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface PySequenceMethods {

  /**
   * implementation of corresponding cpython sq_length
   */
  default PyObject sqLength(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_concat
   */
  default PyObject sqConcat(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_repeat
   */
  default PyObject sqRepeat(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_item
   */
  default PyObject sqItem(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_ass_item
   */
  default PyObject sqAssItem(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_contains
   */
  default PyObject sqContain(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_inplace_concat
   */
  default PyObject sqInplaceContain(PyObject o) {
    return Global.notImplemented;
  }

  /**
   * implementation of corresponding cpython sq_inplace_repeat
   */
  default PyObject sqInplaceRepeat(PyObject o) {
    return Global.notImplemented;
  }
}
