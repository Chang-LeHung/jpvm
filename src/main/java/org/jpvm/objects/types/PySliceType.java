package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PySliceType extends PyTypeType {
  private final PyUnicodeObject name;

  public PySliceType() {
    name = new PyUnicodeObject("slice");
  }


  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }
}
