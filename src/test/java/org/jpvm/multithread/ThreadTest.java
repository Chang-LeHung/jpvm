package org.jpvm.multithread;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

import java.io.IOException;

public class ThreadTest {

  @Test
  public void test01() {
    String file = "src/test/resources/thread/__pycache__/demo01.cpython-38.pyc";
    try {
      PVM pvm = new PVM(file);
      pvm.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test02() {
    String file = "src/test/resources/thread/__pycache__/demo02.cpython-38.pyc";
    try {
      PVM pvm = new PVM(file);
      pvm.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test03() {
    String file = "src/test/resources/thread/__pycache__/demo03.cpython-38.pyc";
    try {
      PVM pvm = new PVM(file);
      pvm.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test04() {
    String file = "src/test/resources/thread/__pycache__/demo04.cpython-38.pyc";
    try {
      PVM pvm = new PVM(file);
      pvm.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test05() {
    String file = "src/test/resources/thread/__pycache__/demo05.cpython-38.pyc";
    try {
      PVM pvm = new PVM(file);
      pvm.run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
