package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeGetAttro {

  default PyObject getAttro() {
    return Global.notImplemented;
  }
}
