package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeHash {

  /**
   * implementation of corresponding cpython tp_hash
   *
   * @return {@link PyLongObject}
   */
  PyLongObject hash();

  @PyClassMethod
  default PyObject __hash__(PyTupleObject args, PyDictObject kwArgs) {
    return hash();
  }
}
