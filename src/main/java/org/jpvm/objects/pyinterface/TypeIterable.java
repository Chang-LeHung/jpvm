package org.jpvm.objects.pyinterface;

import org.jpvm.excptions.objs.PyNotImplemented;

public interface TypeIterable {

  default TypeDoIterate getIterator() throws PyNotImplemented {
    throw new PyNotImplemented("getIterator is not implemented");
  }
}
