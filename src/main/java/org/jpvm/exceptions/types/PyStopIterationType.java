package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.exceptions.pyobjs.PyStopIterationObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyStopIterationType extends PyCommonExceptionType {

  private PyStopIterationType() {
    super(PyStopIterationObject.class);
    name = "StopIteration";
    addBase(0, PyErrorUtils.BaseException);
    addBase(0, PyErrorUtils.Exception);
  }

  public static final class SelfHolder {
    public static PyStopIterationType instance = new PyStopIterationType();
  }

  public static PyStopIterationType getInstance() {
    return SelfHolder.instance;
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyUnicodeObject message = extractPyUnicodeObjectFromArgs(args);
    PyStopIterationObject ret = new PyStopIterationObject(message);
    ret.setType(this);
    return ret;
  }
}
