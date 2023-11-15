package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyAssertionErrorObject extends PyExceptionObject {

  public PyAssertionErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyAssertionErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
