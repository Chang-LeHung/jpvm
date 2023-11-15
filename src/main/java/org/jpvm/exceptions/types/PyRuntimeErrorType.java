package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyRuntimeErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyRuntimeErrorType extends PyExceptionType {
  public PyRuntimeErrorType() {
    name = "RuntimeError";
    addBase(0, PyErrorUtils.Exception);
    this.clazz = PyRuntimeErrorObject.class;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyRuntimeErrorObject ret = new PyRuntimeErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
