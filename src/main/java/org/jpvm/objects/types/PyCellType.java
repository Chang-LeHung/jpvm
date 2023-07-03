package org.jpvm.objects.types;

import org.jpvm.objects.PyCellObject;

public class PyCellType extends PyTypeType {
  public PyCellType() {
    super(PyCellObject.class);
    name = "cell";
  }
}
