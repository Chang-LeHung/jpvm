package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.pyinterface.TypeName;

public class PyComplexType extends PyObject {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyComplexType() {
    name = new PyUnicodeObject("complex");
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
