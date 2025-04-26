package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeInit {

  /**
   * implementation of corresponding cpython tp_init
   *
   * @return {@link PyObject}
   */
  default PyObject init() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "TypeInit init not implemented");
  }
}
