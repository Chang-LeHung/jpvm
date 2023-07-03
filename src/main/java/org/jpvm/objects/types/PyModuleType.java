package org.jpvm.objects.types;

import org.jpvm.objects.PyModuleObject;

public class PyModuleType extends PyTypeType {

  public PyModuleType() {
    super(PyModuleObject.class);
    name = "module";
  }
}
