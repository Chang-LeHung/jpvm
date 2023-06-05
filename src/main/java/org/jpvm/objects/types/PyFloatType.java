package org.jpvm.objects.types;

import org.jpvm.objects.PyLongObject;

public class PyFloatType extends PyTypeType {

  public PyFloatType() {
    super();
    name = "float";
    mro.append(PyLongObject.type);
  }

}
