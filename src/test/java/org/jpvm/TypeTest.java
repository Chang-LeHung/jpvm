package org.jpvm;

import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.junit.Test;

import java.util.Arrays;

public class TypeTest {

  @Test
  public void testPyLongObject() {
    PyLongObject longObject = new PyLongObject(1);
    System.out.println(longObject.getType());
    System.out.println(longObject.getTypeName());
    assert longObject.getTypeName().getData().equals("int");

    PyObject obj = new PyLongObject(1);
    System.out.println(obj.getType());
    System.out.println(obj.getTypeName());
    assert obj.getTypeName().getData().equals("int");
    System.out.println(2.5 % 2);
  }

  @Test
  public void testBytes() {
    byte[] bytes = {1, 2, 3};
    System.out.println(Arrays.toString(bytes));
  }
}
