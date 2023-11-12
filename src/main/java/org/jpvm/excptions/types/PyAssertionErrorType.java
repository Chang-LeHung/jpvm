package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.PyExceptionContext;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;

public class PyAssertionErrorType extends PyExceptionType {

  public PyAssertionErrorType() {
    name = "AssertionError";
    addBase(0, PyErrorUtils.Exception);
  }

  @Override
  public PyExceptionContext call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return super.call(args, kwArgs);
  }
}
