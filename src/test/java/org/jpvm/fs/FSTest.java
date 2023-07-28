package org.jpvm.fs;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

import java.io.IOException;

public class FSTest {

  @Test
  public void test() throws PyException, IOException {
    String filename = "src/test/resources/fs/__pycache__/Demo01.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }
}
