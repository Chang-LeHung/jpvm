package org.jpvm.exceptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyKeyErrorObject extends PyExceptionObject {
  public PyKeyErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyKeyErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
