package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeDoIterate {

  default PyObject next() {
    return Global.notImplemented;
  }
}
