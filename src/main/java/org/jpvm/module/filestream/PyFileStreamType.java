package org.jpvm.module.filestream;

import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTypeType;

public class PyFileStreamType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyFileStreamType() {
    super(PyFileStreamObject.class);
    name = new PyUnicodeObject("bool");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }
}
