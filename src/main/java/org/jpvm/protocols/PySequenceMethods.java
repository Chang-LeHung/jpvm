package org.jpvm.protocols;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyObject;

public interface PySequenceMethods {

  /** implementation of corresponding cpython sq_length */
  default PyObject sqLength(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("sqLength not implemented");
  }

  /** implementation of corresponding cpython sq_concat */
  default PyObject sqConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    throw new PyNotImplemented("sqConcat not implemented");
  }

  /** implementation of corresponding cpython sq_repeat */
  default PyObject sqRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    throw new PyNotImplemented("sqRepeat not implemented");
  }

  /** implementation of corresponding cpython sq_item */
  default PyObject sqItem(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    throw new PyNotImplemented("sqItem not implemented");
  }

  /** implementation of corresponding cpython sq_ass_item */
  default PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    throw new PyNotImplemented("sqAssItem not implemented");
  }

  /** implementation of corresponding cpython sq_contains */
  default PyObject sqContain(PyObject o) throws PyException {
    throw new PyNotImplemented("sqContain not implemented");
  }

  /** implementation of corresponding cpython sq_inplace_concat */
  default PyObject sqInplaceConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    throw new PyNotImplemented("sqInplaceConcat not implemented");
  }

  /** implementation of corresponding cpython sq_inplace_repeat */
  default PyObject sqInplaceRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    throw new PyNotImplemented("sqInplaceRepeat not implemented");
  }
}
