package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.pyobjs.PyExceptionObject;
import org.jpvm.excptions.pyobjs.PyNameErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyNameErrorType extends PyExceptionType {

  public PyNameErrorType() {
    name = "NameError";
    addBase(0, PyErrorUtils.Exception);
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyNameErrorObject ret = new PyNameErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
