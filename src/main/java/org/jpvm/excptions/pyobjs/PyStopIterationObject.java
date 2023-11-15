package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyStopIterationObject extends PyExceptionObject {
  public PyStopIterationObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyStopIterationObject(String errorMsg) {
    super(errorMsg);
  }
}
