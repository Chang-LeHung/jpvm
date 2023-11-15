package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyTypeErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyTypeErrorType extends PyExceptionType {
  public PyTypeErrorType() {
    name = "TypeError";
    addBase(0, PyErrorUtils.Exception);
    this.clazz = PyTypeErrorObject.class;
  }


  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyTypeErrorObject ret = new PyTypeErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
