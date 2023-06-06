package org.jpvm.objects.types;

import org.jpvm.objects.PyFloatObject;
import org.jpvm.objects.PyLongObject;

public class PyComplexType extends PyTypeType {

  public PyComplexType() {
    super();
    name = "complex";
    mro.add(PyLongObject.type);
    mro.add(PyFloatObject.type);
  }
}
