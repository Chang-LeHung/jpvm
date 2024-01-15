package org.jpvm.objects.types;

import org.jpvm.objects.PySliceObject;

public class PySliceType extends PyTypeType {
  private PySliceType() {
    super(PySliceObject.class);
    name = "slice";
  }

  public static final class SelfHolder {
    public static final PySliceType self = new PySliceType();
  }

  public static PySliceType getInstance() {
    return SelfHolder.self;
  }
}
