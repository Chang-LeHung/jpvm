package org.jpvm.exceptions.pyobjs;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyStopIterationObject extends PyExceptionObject {
  private PyObject retVal;

  public PyStopIterationObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyStopIterationObject(String errorMsg) {
    super(errorMsg);
  }

  public PyObject getRetVal() {
    return retVal;
  }

  public void setRetVal(PyObject retVal) {
    this.retVal = retVal;
  }
}
