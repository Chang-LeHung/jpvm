package org.jpvm.testobj;

import java.io.IOException;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

public class TestSuper {

  @Test
  public void testSuper01() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test08.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void testSuper02() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test09.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void testSuper03() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test10.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void testCall() {
    String filename = "src/test/resources/obsy/__pycache__/test06.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(filename);
      JPVM.run();
      System.out.println(JPVM.call("fib", 10));
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test09() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test09.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }
}
