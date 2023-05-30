package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyBaseObjectType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyBaseObjectType() {
    name = new PyUnicodeObject("object");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
