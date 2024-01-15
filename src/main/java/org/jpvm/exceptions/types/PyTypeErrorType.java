package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyTypeErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyTypeErrorType extends PyCommonExceptionType {
  private PyTypeErrorType() {
    super(PyTypeErrorObject.class);
    name = "TypeError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyTypeErrorType instance = new PyTypeErrorType();
  }

  public static PyTypeErrorType getInstance() {
    return SelfHolder.instance;
  }


  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyTypeErrorObject ret = new PyTypeErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
