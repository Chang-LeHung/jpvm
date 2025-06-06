package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyAttributeErrorObject;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyAttributeErrorType extends PyCommonExceptionType {

  private PyAttributeErrorType() {
    super(PyAttributeErrorType.class);
    name = "AttributeError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static final PyAttributeErrorType self = new PyAttributeErrorType();
  }

  public static PyAttributeErrorType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyAttributeErrorObject ret = new PyAttributeErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
