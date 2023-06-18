package org.jpvm.testobj;

import org.jpvm.errors.PyException;
import org.jpvm.objects.PyListObject;
import org.jpvm.objects.PyObject;
import org.jpvm.pvm.PVM;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestObjectSystem {

  @Test
  public void testObject() {
    PyObject list = new PyListObject();
    PyObject type = list.getType();
    System.out.println(type);
    Assert.assertEquals(type, PyListObject.type);
  }

  @Test
  public void testClass01() {
    String filename = "src/test/resources/obsy/__pycache__/test01.cpython-38.pyc";
    try {
      new PVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testClass02() {
    String filename = "src/test/resources/obsy/__pycache__/test02.cpython-38.pyc";
    try {
      new PVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
