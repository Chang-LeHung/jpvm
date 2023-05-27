package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyObject;

public interface TypeInit {

  /**
   * implementation of corresponding cpython tp_init
   * @return {@link PyObject}
   */
  PyObject init();
}
