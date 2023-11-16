package org.jpvm.objects.types;

import org.jpvm.objects.PyComplexObject;

public class PyComplexType extends PyTypeType {

  private PyComplexType() {
    super(PyComplexObject.class);
    name = "complex";
  }

  public static final class SelfHolder {
    public static final PyComplexType self = new PyComplexType();
  }

  public static PyComplexType getInstance() {
    return SelfHolder.self;
  }
}
