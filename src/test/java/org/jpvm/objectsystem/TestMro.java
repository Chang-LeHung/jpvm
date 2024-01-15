package org.jpvm.objectsystem;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

import java.io.IOException;

public class TestMro {

  public void run(String filename) throws PyException, IOException {
    JPVM jpvm = new JPVM(filename);
    jpvm.run();
  }

  @Test
  public void testList() throws PyException, IOException {
    run("src/test/resources/objectsystem/__pycache__/testlist.cpython-38.pyc");
  }
}
