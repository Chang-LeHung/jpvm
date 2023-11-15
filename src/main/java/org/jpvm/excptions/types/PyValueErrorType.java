package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.pyobjs.PyExceptionObject;
import org.jpvm.excptions.pyobjs.PyValueErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyValueErrorType extends PyExceptionType {
  public PyValueErrorType() {
    name = "ValueError";
    addBase(0, PyErrorUtils.Exception);
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyValueErrorObject ret = new PyValueErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
