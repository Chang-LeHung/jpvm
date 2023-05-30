package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyComplexType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyComplexType() {
    name = new PyUnicodeObject("complex");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
