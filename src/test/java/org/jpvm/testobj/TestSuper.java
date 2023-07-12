package org.jpvm.testobj;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

import java.io.IOException;

public class TestSuper {

  @Test
  public void testSuper01() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test08.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }

  @Test
  public void testSuper02() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test09.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }

  @Test
  public void testSuper03() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test10.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }
}
