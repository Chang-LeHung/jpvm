package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyRuntimeErrorObject extends PyExceptionObject {
  public PyRuntimeErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyRuntimeErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
