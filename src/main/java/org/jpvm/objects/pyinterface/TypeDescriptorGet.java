package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeDescriptorGet {

  default PyObject descrGet(PyObject obj, PyObject cls) throws PyNotImplemented {
    throw new PyNotImplemented("TypeDescriptor descrGet not implemented");
  }

}
