package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.objects.PyObject;

public interface TypeDescriptorSet {
  default PyObject descrSet(PyObject obj, PyObject val) throws PyNotImplemented {
    throw new PyNotImplemented("TypeDescriptor descrSet not implemented");
  }
}
