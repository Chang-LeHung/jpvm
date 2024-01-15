package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;

public interface TypeIterable {

  default TypeDoIterate getIterator() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "getIterator is not implemented");
    return null;
  }
}
