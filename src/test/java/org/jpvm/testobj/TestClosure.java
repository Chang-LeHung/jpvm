package org.jpvm.testobj;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

import java.io.IOException;

public class TestClosure {

  @Test
  public void test01() throws PyException, IOException {
    String filename = "src/test/resources/closure/__pycache__/test02.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }

  @Test
  public void test02() throws PyException, IOException {
    String filename = "src/test/resources/closure/__pycache__/test03.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }

  @Test
  public void test03() throws PyException, IOException {
    String filename = "src/test/resources/closure/__pycache__/test04.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }
}
