package org.jpvm.testobj;

import java.io.IOException;
import org.jpvm.errors.PyException;
import org.jpvm.pvm.JPVM;
import org.junit.Test;

public class TestClosure {

  @Test
  public void test01() throws PyException, IOException {
    String filename = "src/test/resources/closure/__pycache__/test02.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void test02() throws PyException, IOException {
    String filename = "src/test/resources/closure/__pycache__/test03.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void test03() throws PyException, IOException {
    String filename = "src/test/resources/closure/__pycache__/test04.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }
}
