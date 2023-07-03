package org.jpvm.objects.types;

import org.jpvm.objects.PyGeneratorObject;

public class PyGeneratorType extends PyTypeType {

  public PyGeneratorType() {
    super(PyGeneratorObject.class);
    name = "generator";
  }
}
