package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyStackOverflowErrorObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyStackOverflowType extends PyCommonExceptionType {

  private PyStackOverflowType() {
    super(PyStackOverflowErrorObject.class);
//    name = "StackOverflow";
    name = "RecursionError";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static  final PyStackOverflowType instance = new PyStackOverflowType();
  }

  public static PyStackOverflowType getInstance() {
    return SelfHolder.instance;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyStackOverflowErrorObject ret = new PyStackOverflowErrorObject(message);
    ret.setType(this);
    return ret;
  }
}
