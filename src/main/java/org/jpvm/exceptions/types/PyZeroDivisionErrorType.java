package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyZeroDivisionErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyZeroDivisionErrorType extends PyExceptionType {
  public PyZeroDivisionErrorType() {
    name = "ZeroDivisionError";
    addBase(0, PyErrorUtils.Exception);
    this.clazz = PyZeroDivisionErrorObject.class;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyZeroDivisionErrorObject ret = new PyZeroDivisionErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
