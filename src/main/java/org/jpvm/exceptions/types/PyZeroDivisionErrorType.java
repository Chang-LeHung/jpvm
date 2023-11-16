package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyZeroDivisionErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyZeroDivisionErrorType extends PyCommonExceptionType {
  public PyZeroDivisionErrorType() {
    super(PyZeroDivisionErrorObject.class);
    name = "ZeroDivisionError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static PyZeroDivisionErrorType instance = new PyZeroDivisionErrorType();
  }

  public static PyZeroDivisionErrorType getInstance() {
    return SelfHolder.instance;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyZeroDivisionErrorObject ret = new PyZeroDivisionErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
