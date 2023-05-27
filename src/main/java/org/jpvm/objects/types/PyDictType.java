package org.jpvm.objects.types;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.pyinterface.TypeName;

public class PyDictType extends PyObject {

  private final PyUnicodeObject name;

  public Object parentType = PyTypeType.parentType;

  public PyDictType() {
    this.name = new PyUnicodeObject("dict");
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
