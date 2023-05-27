package org.jpvm.protocols;

import org.jpvm.errors.PyIndexOutOfBound;
import org.jpvm.errors.PyKeyError;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyObject;

public interface PyMappingMethods {

  /**
   * implementation of corresponding cpython mp_length
   */
  default PyObject mpLength(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("mpLength is not implemented");
  }

  /**
   * implementation of corresponding cpython mp_subscript
   */
  default PyObject mpSubscript(PyObject o) throws PyIndexOutOfBound, PyKeyError, PyTypeNotMatch, PyNotImplemented {
    throw new PyNotImplemented("mpSubscript is not implemented");
  }

  /**
   * implementation of corresponding cpython mp_ass_subscript
   */
  default PyObject mpAssSubscript(PyObject key, PyObject val) throws PyKeyError, PyNotImplemented {
    throw new PyNotImplemented("mpAssSubscript is not implemented");
  }
}
