package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeSetAttro {

  default PyObject setAttro(PyObject key, PyObject val) throws PyException {
    throw new PyNotImplemented("TypeSetAttro setAttro not implemented");
  }
}
