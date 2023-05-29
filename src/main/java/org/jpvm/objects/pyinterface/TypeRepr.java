package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyUnicodeObject;

public interface TypeRepr {

  /**
   * implementation of corresponding cpython tp_repr
   *
   * @return {@link PyUnicodeObject}
   */
  PyUnicodeObject repr();
}
