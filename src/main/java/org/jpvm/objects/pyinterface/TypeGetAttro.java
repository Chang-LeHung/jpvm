package org.jpvm.objects.pyinterface;

import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.objs.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeGetAttro {

  default PyObject getAttro(PyObject key) throws PyException {
    throw new PyNotImplemented("TypeGetAttro getAttro not implemented");
  }
}
