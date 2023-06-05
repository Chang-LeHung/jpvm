package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;

public interface TypeIterable {

  default TypeDoIterate getIterator() throws PyNotImplemented {
    throw new PyNotImplemented("getIterator is not implemented");
  }
}
