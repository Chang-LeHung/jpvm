package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.internal.Global;
import org.jpvm.objects.PyObject;

public interface TypeSetAttro {

  default PyObject setAttro() throws PyNotImplemented {
    throw new PyNotImplemented("TypeSetAttro setAttro not implemented");
  }
}
