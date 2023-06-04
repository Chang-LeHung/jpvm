package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyIndexOutOfBound;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyObject;
import org.jpvm.python.BuiltIn;

public interface TypeDoIterate {

  default PyObject next() throws PyException {
    return BuiltIn.PyExcStopIteration;
  }

  default PyObject get(int idx) throws PyIndexOutOfBound, PyNotImplemented {
    throw new PyNotImplemented("TypeDoIterate get not implemented");
  }

  default int size() {
    return -1;
  }

  default boolean hasNext() {
    return false;
  }
}
