package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyImportErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyImportErrorType extends PyCommonExceptionType {

  public PyImportErrorType() {
    super(PyImportErrorType.class);
    name = "ImportError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyImportErrorType self = new PyImportErrorType();
  }

  public static PyImportErrorType getInstance() {
    return SelfHolder.self;
  }


  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {

    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyImportErrorObject ret = new PyImportErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
