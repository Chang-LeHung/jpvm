package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyRuntimeErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyRuntimeErrorType extends PyCommonExceptionType {
  private PyRuntimeErrorType() {
    super(PyRuntimeErrorObject.class);
    name = "RuntimeError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyRuntimeErrorType instance = new PyRuntimeErrorType();
  }

  public static PyRuntimeErrorType getInstance() {
    return SelfHolder.instance;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyRuntimeErrorObject ret = new PyRuntimeErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
