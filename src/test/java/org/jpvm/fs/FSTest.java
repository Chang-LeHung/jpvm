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

  @Test
  public void testCopyFile() throws PyException, IOException {
    String filename = "src/test/resources/fs/__pycache__/copyfile.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }

  @Test
  public void testCopyDir() throws PyException, IOException {
    String filename = "src/test/resources/fs/__pycache__/copydir.cpython-38.pyc";
    PVM pvm = new PVM(filename);
    pvm.run();
  }
}
