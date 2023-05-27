package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeGetAttro {

  default PyObject getAttro() throws PyNotImplemented {
    throw new PyNotImplemented("TypeGetAttro getAttro not implemented");
  }
}
