package org.jpvm.objects.types;

import org.jpvm.objects.PyGeneratorObject;

public class PyGeneratorType extends PyTypeType {

  private PyGeneratorType() {
    super(PyGeneratorObject.class);
    name = "generator";
  }

  public static final class SelfHolder {
    public static final PyGeneratorType self = new PyGeneratorType();
  }

  public static PyGeneratorType getInstance() {
    return SelfHolder.self;
  }
}
