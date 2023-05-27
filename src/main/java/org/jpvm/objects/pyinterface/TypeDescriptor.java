package org.jpvm.objects.pyinterface;

import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeDescriptor {

  default PyObject descrGet(PyObject obj, PyObject cls) {
    return Global.notImplemented;
  }

  default PyObject descrSet(PyObject obj, PyObject val) {
    return Global.notImplemented;
  }
}
