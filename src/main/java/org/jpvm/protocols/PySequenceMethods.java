package org.jpvm.protocols;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.PyObject;

public interface PySequenceMethods {

  /** implementation of corresponding cpython sq_length */
  default PyObject sqLength(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqLength not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_concat */
  default PyObject sqConcat(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqConcat not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_repeat */
  default PyObject sqRepeat(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqRepeat not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_item */
  default PyObject sqItem(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqItem not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_ass_item */
  default PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqAssItem not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_contains */
  default PyObject sqContain(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqContain not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_inplace_concat */
  default PyObject sqInplaceConcat(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqInplaceConcat not implemented");
    return null;
  }

  /** implementation of corresponding cpython sq_inplace_repeat */
  default PyObject sqInplaceRepeat(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqInplaceRepeat not implemented");
    return null;
  }
}
