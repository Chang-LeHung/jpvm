package org.jpvm.objects.types;

import org.jpvm.objects.PyListObject;

public class PyBaseObjectType extends PyTypeType {

  public PyBaseObjectType() {
    mro = new PyListObject();
    name = "object";
  }
}
