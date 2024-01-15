package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.types.PyTypeType;

public interface TypeCheck {

  /**
   * @return type of this class
   */
  PyObject getType();

  default void setType(PyTypeType type) {}
}
