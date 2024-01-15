package org.jpvm.objects.types;

import org.jpvm.objects.PyNoneObject;

public class PyNoneType extends PyTypeType {
  private PyNoneType() {
    super(PyNoneObject.class);
    name = "none";
  }

  public static final class SelfHolder {
    public static final PyNoneType self = new PyNoneType();
  }

  public static PyNoneType getInstance() {
    return SelfHolder.self;
  }
}
