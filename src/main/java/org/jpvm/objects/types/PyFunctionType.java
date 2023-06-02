package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyFunctionType extends PyTypeType{
  private final PyUnicodeObject name;

  public PyFunctionType() {
    name = new PyUnicodeObject("frame");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }
}
