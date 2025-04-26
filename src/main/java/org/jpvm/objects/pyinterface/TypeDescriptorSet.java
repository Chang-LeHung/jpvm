package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeDescriptorSet {
  default PyObject descrSet(PyObject obj, PyObject val) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "TypeDescriptor descrSet not implemented");
  }
}
