package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyKeyErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyKeyErrorType extends PyCommonExceptionType {

  private PyKeyErrorType() {
    super(PyKeyErrorObject.class);
    name = "KeyError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyKeyErrorType self = new PyKeyErrorType();
  }

  public static PyKeyErrorType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyKeyErrorObject ret = new PyKeyErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
