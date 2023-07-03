package org.jpvm.objects.types;

import org.jpvm.objects.PyComplexObject;

public class PyComplexType extends PyTypeType {

  public PyComplexType() {
    super(PyComplexObject.class);
    name = "complex";
    //    mro.add(PyLongObject.type);
    //    mro.add(PyFloatObject.type);
  }
}
