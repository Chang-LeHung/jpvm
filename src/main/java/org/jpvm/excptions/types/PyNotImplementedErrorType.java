package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.pyobjs.PyExceptionObject;
import org.jpvm.excptions.pyobjs.PyNotImplementedErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyNotImplementedErrorType extends PyExceptionType {

  public PyNotImplementedErrorType() {
    name = "NotImplementedError";
    addBase(0, PyErrorUtils.Exception);
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyNotImplementedErrorObject ret = new PyNotImplementedErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
