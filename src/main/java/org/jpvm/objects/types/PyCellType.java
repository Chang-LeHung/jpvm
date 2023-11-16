package org.jpvm.objects.types;

import org.jpvm.objects.PyCellObject;

public class PyCellType extends PyTypeType {
  private PyCellType() {
    super(PyCellObject.class);
    name = "cell";
  }

  public static final class SelfHolder {
    public static PyCellType self = new PyCellType();
  }

  public static PyCellType getInstance() {
    return SelfHolder.self;
  }
}
