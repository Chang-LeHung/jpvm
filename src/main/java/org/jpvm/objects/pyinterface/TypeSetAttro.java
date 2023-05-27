package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeSetAttro {

  default PyObject setAttro() {
    return Global.notImplemented;
  }
}
