package org.jpvm.objects;

import org.junit.Test;

public class PyTypeTest {

  @Test
  public void testTuple() {
    PyTupleObject object = new PyTupleObject(0);
    System.out.println(object.getType().getTypeName());
    System.out.println(PyTupleObject.check(PyTupleObject.type));
  }
}
