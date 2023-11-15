package org.jpvm.exceptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyNotImplementedErrorObject extends PyExceptionObject {
  public PyNotImplementedErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyNotImplementedErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
