package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeSetAttr {

  /**
   * implementation of corresponding cpython tp_setattr
   * @return {@link PyObject}
   */
  default PyObject setAttr(PyObject key, PyObject val) {
    return Global.notImplemented;
  }
}
