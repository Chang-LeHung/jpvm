package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public interface TypeName {

  /**
   * implementation of corresponding cpython tp_name
   * @return {@link PyObject}
   */
  PyUnicodeObject getTypeName();
}
