package org.jpvm.objects.pyinterface;

import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.objs.PyNotImplemented;
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
