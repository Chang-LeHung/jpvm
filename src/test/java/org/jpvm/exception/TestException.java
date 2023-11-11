package org.jpvm.exception;

import java.io.IOException;
import org.jpvm.excptions.objs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

public class TestException {

  @Test
  public void test01() {
    String file = "src/test/resources/exception/__pycache__/demo01.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test02() {
    String file = "src/test/resources/exception/__pycache__/demo02.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test03() {
    String file = "src/test/resources/exception/__pycache__/demo03.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test04() {
    String file = "src/test/resources/exception/__pycache__/demo04.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test05() {
    String file = "src/test/resources/exception/__pycache__/demo05.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test06() {
    String file = "src/test/resources/exception/__pycache__/demo06.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test07() {
    String file = "src/test/resources/exception/__pycache__/demo07.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test08() {
    String file = "src/test/resources/exception/__pycache__/demo08.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test09() {
    String file = "src/test/resources/exception/__pycache__/demo09.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test10() {
    String file = "src/test/resources/exception/__pycache__/demo10.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test11() {
    String file = "src/test/resources/exception/__pycache__/demo11.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
