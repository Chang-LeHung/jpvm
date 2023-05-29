package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyLongObject;

public interface TypeHash {

  /**
   * implementation of corresponding cpython tp_hash
   *
   * @return {@link PyLongObject}
   */
  PyLongObject hash();
}
