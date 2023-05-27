package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeCall {

  /**
   * implementation of corresponding cpython tp_call
   * @return {@link PyObject}
   */
   default PyObject call() {
     return Global.notImplemented;
   }
}
