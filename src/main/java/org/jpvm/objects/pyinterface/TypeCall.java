package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;

public interface TypeCall {

  /**
   * implementation of corresponding cpython tp_call
   *
   * @return {@link PyObject}
   */
  default PyObject call(PyObject self, PyTupleObject args,
                        PyDictObject kwArgs) throws PyException {
    throw new PyNotImplemented("TypeCall call not implemented");
  }
}
