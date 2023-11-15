package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyIndexOutOfBoundErrorObject extends PyExceptionObject {
  public PyIndexOutOfBoundErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyIndexOutOfBoundErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
