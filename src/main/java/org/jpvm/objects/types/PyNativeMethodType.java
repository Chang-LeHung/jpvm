package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyNativeMethodType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyNativeMethodType() {
    this.name = new PyUnicodeObject("native_method");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

}
