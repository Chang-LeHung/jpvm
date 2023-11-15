package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyFileNotFoundErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyFileNotFoundErrorType extends PyExceptionType {

  public PyFileNotFoundErrorType() {
    name = "FileNotFoundError";
    addBase(0, PyErrorUtils.Exception);
    this.clazz = PyFileNotFoundErrorObject.class;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyFileNotFoundErrorObject ret = new PyFileNotFoundErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
