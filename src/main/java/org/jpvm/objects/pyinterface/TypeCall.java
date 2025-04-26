package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeCall {

  /**
   * implementation of corresponding cpython tp_call
   *
   * @return {@link PyObject}
   */
  default PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "TypeCall call not implemented");
  }

  @PyClassMethod
  default PyObject __call__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return call(args, kwArgs);
  }
}
