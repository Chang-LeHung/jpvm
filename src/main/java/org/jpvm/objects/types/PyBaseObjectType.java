package org.jpvm.objects.types;

import java.util.ArrayList;
import org.jpvm.objects.PyObject;

public class PyBaseObjectType extends PyTypeType {

  public static PyObject type = PyTypeType.type;

  public PyBaseObjectType() {
    mro = new ArrayList<>();
    name = "object";
  }

  @Override
  public PyObject getType() {
    return type;
  }
}
