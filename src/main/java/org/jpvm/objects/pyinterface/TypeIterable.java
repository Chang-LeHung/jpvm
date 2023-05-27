package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeIterable {

  default PyObject getIterator() {
    return Global.notImplemented;
  }
}
