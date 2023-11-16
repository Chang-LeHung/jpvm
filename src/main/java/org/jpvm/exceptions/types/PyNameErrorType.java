package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyNameErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyNameErrorType extends PyCommonExceptionType {

  private PyNameErrorType() {
    super(PyNameErrorObject.class);
    name = "NameError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static PyNameErrorType self = new PyNameErrorType();
  }

  public static PyNameErrorType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyNameErrorObject ret = new PyNameErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
