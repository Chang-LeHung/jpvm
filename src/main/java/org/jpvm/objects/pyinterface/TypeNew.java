package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeNew {

  /**
   * implementation of corresponding cpython tp_new
   * @return {@link PyObject}
   */
  default PyObject allocate() {
    return Global.notImplemented;
  }
}
