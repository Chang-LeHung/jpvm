package org.jpvm.testobj;

import java.io.IOException;
import org.jpvm.errors.PyException;
import org.jpvm.module.sys.Sys;
import org.jpvm.objects.PyListObject;
import org.jpvm.objects.PyObject;
import org.jpvm.pvm.PVM;
import org.junit.Assert;
import org.junit.Test;

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

  @Test
  public void testCall() {
    String filename = "src/test/resources/obsy/__pycache__/test06.cpython-38.pyc";
    try {
      PVM pvm = new PVM(filename);
      pvm.run();
      long s = System.currentTimeMillis();
      System.out.println(pvm.call("fib", 10));
      long t = System.currentTimeMillis();
      System.out.println((t - s));
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
