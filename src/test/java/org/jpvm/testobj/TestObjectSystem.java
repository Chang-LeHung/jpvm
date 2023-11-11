package org.jpvm.testobj;

import java.io.IOException;
import org.jpvm.excptions.objs.PyException;
import org.jpvm.objects.PyListObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.vm.JPVM;
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
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testClass02() {
    String filename = "src/test/resources/obsy/__pycache__/test02.cpython-38.pyc";
    try {
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCall() {
    String filename = "src/test/resources/obsy/__pycache__/test06.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(filename);
      JPVM.run();
      System.out.println(JPVM.call("fib", 10));
      PyListObject list = new PyListObject();
      var type = (PyTypeType) list.getType();
      System.out.println(type.getMro());
      System.out.println(PyObject.type);
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
