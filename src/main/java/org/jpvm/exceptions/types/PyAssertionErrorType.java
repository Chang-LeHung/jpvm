package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyAssertionErrorObject;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyAssertionErrorType extends PyCommonExceptionType {

  private PyAssertionErrorType() {
    super(PyAssertionErrorObject.class);
    name = "AssertionError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyAssertionErrorType self = new PyAssertionErrorType();
  }

  public static PyAssertionErrorType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyAssertionErrorObject ret = new PyAssertionErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
