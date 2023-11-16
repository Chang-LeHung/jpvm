package org.jpvm.objects.types;

import org.jpvm.objects.PyFrameObject;

public class PyFrameType extends PyTypeType {
  private PyFrameType() {
    super(PyFrameObject.class);
    name = "frame";
  }

  public static final class SelfHolder {
    public static final PyFrameType self =  new PyFrameType();
  }

  public static PyFrameType getInstance() {
    return SelfHolder.self;
  }
}
