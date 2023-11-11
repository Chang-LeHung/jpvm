package org.jpvm.objects.pyinterface;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.jobjs.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeGetAttro {

  default PyObject getAttro(PyObject key) throws PyException {
    throw new PyNotImplemented("TypeGetAttro getAttro not implemented");
  }
}
