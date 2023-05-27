package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeIterable {

  default PyObject getIterator() throws PyNotImplemented {
    throw new PyNotImplemented("getIterator is not implemented");
  }
}
