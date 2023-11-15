package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyStackOverflowErrorObject extends PyExceptionObject {
  public PyStackOverflowErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyStackOverflowErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
