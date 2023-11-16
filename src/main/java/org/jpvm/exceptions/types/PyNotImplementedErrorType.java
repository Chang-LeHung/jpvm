package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyNotImplementedErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyNotImplementedErrorType extends PyCommonExceptionType {

  private PyNotImplementedErrorType() {
    super(PyNotImplementedErrorObject.class);
    name = "NotImplementedError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyNotImplementedErrorType self = new PyNotImplementedErrorType();
  }

  public static PyNotImplementedErrorType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyNotImplementedErrorObject ret = new PyNotImplementedErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
