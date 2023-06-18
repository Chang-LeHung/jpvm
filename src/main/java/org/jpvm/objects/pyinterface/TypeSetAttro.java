package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeSetAttro {

  default PyObject setAttro(PyObject key, PyObject val) throws PyException {
    throw new PyNotImplemented("TypeSetAttro setAttro not implemented");
  }
}
