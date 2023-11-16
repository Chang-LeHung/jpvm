package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyIndexOutOfBoundErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyIndexOutOfBoundErrorType extends PyCommonExceptionType {
  private PyIndexOutOfBoundErrorType() {
    super(PyIndexOutOfBoundErrorObject.class);
    name = "IndexError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyIndexOutOfBoundErrorType self = new PyIndexOutOfBoundErrorType();
  }

  public static PyIndexOutOfBoundErrorType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyIndexOutOfBoundErrorObject ret = new PyIndexOutOfBoundErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
