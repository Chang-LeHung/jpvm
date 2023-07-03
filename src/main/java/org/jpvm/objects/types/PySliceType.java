package org.jpvm.objects.types;

import org.jpvm.objects.PySliceObject;

public class PySliceType extends PyTypeType {
  public PySliceType() {
    super(PySliceObject.class);
    name = "slice";
  }
}
