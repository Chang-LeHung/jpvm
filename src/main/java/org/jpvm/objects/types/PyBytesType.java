package org.jpvm.objects.types;

import org.jpvm.objects.PyBytesObject;

public class PyBytesType extends PyTypeType {

  private PyBytesType() {
    super(PyBytesObject.class);
    name = "bytes";
  }

  public static final class SelfHolder {
    public static final PyBytesType self = new PyBytesType();
  }

  public static PyBytesType getInstance() {
    return SelfHolder.self;
  }
}
