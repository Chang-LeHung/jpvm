package org.jpvm.multithread;

import java.io.IOException;
import org.jpvm.errors.PyException;
import org.jpvm.pvm.JPVM;
import org.junit.Test;

public class ThreadTest {

  @Test
  public void test01() {
    String file = "src/test/resources/thread/__pycache__/demo01.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test02() {
    String file = "src/test/resources/thread/__pycache__/demo02.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test03() {
    String file = "src/test/resources/thread/__pycache__/demo03.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test04() {
    String file = "src/test/resources/thread/__pycache__/demo04.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test05() {
    String file = "src/test/resources/thread/__pycache__/demo05.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test06() {
    String file = "src/test/resources/thread/__pycache__/demo06.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test07() {
    String file = "src/test/resources/thread/__pycache__/demo07.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(file);
      JPVM.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
