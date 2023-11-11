package org.jpvm.fs;

import java.io.IOException;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

public class FSTest {

  @Test
  public void test() throws PyException, IOException {
    String filename = "src/test/resources/fs/__pycache__/Demo01.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void testCopyFile() throws PyException, IOException {
    String filename = "src/test/resources/fs/__pycache__/copyfile.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }

  @Test
  public void testCopyDir() throws PyException, IOException {
    String filename = "src/test/resources/fs/__pycache__/copydir.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }
}
