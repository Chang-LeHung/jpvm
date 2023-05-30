package org.jpvm.objects.types;

import org.jpvm.objects.PyUnicodeObject;

public class PyLongType extends PyTypeType {

  private final PyUnicodeObject name;

  public PyLongType() {
    this.name = new PyUnicodeObject("int");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

  @Override
  public String toString() {
    return "PyLongType{" +
        "name=" + name.getData() +
        '}';
  }
}
