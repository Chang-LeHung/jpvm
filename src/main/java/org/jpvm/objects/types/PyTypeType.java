package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.pyinterface.TypeName;

public class PyTypeType extends PyObject {

  private final PyUnicodeObject name;

  public static Object parentType = new PyTypeType();

  public PyTypeType() {
    name = new PyUnicodeObject("type");
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return name;
  }

  @Override
  public Object getType() {
    return parentType;
  }
}
