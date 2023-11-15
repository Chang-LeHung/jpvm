package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyAttributeErrorObject extends PyExceptionObject {
  public PyAttributeErrorObject(PyUnicodeObject errorMsg) {
    super(errorMsg);
  }

  public PyAttributeErrorObject(String errorMsg) {
    super(errorMsg);
  }
}
