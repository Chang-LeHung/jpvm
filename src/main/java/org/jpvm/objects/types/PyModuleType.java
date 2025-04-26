package org.jpvm.objects.types;

import org.jpvm.objects.PyModuleObject;

public class PyModuleType extends PyTypeType {

  private PyModuleType() {
    super(PyModuleObject.class);
    name = "module";
  }

  public static final class SelfHolder {
    public static final PyModuleType self = new PyModuleType();
  }

  public static PyModuleType getInstance() {
    return SelfHolder.self;
  }
}
