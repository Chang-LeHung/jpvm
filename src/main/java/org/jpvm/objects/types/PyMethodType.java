package org.jpvm.objects.types;

import org.jpvm.objects.PyMethodObject;

public class PyMethodType extends PyTypeType {
  private PyMethodType() {
    super(PyMethodObject.class);
    name = "method";
  }

  public static final class SelfHolder {
    public static final PyMethodType self = new PyMethodType();
  }

  public static PyMethodType getInstance() {
    return SelfHolder.self;
  }
}
