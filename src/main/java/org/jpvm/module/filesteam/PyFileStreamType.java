package org.jpvm.module.filesteam;

import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTypeType;

public class PyFileStreamType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyFileStreamType() {
    name = new PyUnicodeObject("bool");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
