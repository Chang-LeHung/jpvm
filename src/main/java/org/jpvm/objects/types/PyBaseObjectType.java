package org.jpvm.objects.types;

import java.util.ArrayList;
import org.jpvm.objects.PyObject;

public class PyBaseObjectType extends PyTypeType {

  public static final PyTypeType type = PyTypeType.type;

  private PyBaseObjectType() {
    super(PyObject.class);
    mro = new ArrayList<>();
    name = "object";
  }

  @Override
  public PyObject getType() {
    return type;
  }

  public static final class SelfHolder {
    public static final PyBaseObjectType self = new PyBaseObjectType();
  }

  public static PyBaseObjectType getInstance() {
    return SelfHolder.self;
  }
}
