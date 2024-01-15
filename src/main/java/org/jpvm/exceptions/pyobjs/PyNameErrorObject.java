package org.jpvm.exceptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyNameErrorObject extends PyExceptionObject {
  public PyNameErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyNameErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
