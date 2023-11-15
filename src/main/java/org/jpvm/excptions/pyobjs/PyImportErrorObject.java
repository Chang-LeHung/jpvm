package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyImportErrorObject extends PyExceptionObject {
  public PyImportErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyImportErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
