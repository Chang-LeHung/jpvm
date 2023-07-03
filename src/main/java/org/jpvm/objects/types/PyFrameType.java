package org.jpvm.objects.types;

import org.jpvm.objects.PyFrameObject;

public class PyFrameType extends PyTypeType {
  public PyFrameType() {
    super(PyFrameObject.class);
    name = "frame";
  }
}
