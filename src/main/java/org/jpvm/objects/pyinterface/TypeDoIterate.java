package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeDoIterate {

  default PyObject next() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
  }

  default PyObject get(int idx) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "TypeDoIterate get not implemented");
  }

  default int size() {
    return -1;
  }

  default boolean hasNext() {
    return false;
  }
}
