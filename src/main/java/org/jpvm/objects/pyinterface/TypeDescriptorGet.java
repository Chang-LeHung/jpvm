package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;

public interface TypeDescriptorGet {

  default PyObject descrGet(PyObject obj, PyObject cls) throws PyException {
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "TypeDescriptor descrGet not implemented");
  }
}
