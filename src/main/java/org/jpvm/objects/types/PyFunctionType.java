package org.jpvm.objects.types;

import org.jpvm.objects.PyFunctionObject;

public class PyFunctionType extends PyTypeType {
  public PyFunctionType() {
    super(PyFunctionObject.class);
    name = "function";
  }
}
