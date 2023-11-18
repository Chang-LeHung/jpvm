package org.jpvm.protocols;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.PyObject;

public interface PySequenceMethods {

  /** implementation of corresponding cpython sq_length */
  default PyObject sqLength(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqLength not implemented");
  }

  /** implementation of corresponding cpython sq_concat */
  default PyObject sqConcat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqConcat not implemented");
  }

  /** implementation of corresponding cpython sq_repeat */
  default PyObject sqRepeat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqRepeat not implemented");
  }

  /** implementation of corresponding cpython sq_item */
  default PyObject sqItem(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqItem not implemented");
  }

  /** implementation of corresponding cpython sq_ass_item */
  default PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqAssItem not implemented");
  }

  /** implementation of corresponding cpython sq_contains */
  default PyObject sqContain(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqContain not implemented");
  }

  /** implementation of corresponding cpython sq_inplace_concat */
  default PyObject sqInplaceConcat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqInplaceConcat not implemented");
  }

  /** implementation of corresponding cpython sq_inplace_repeat */
  default PyObject sqInplaceRepeat(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sqInplaceRepeat not implemented");
  }
}
