package org.jpvm.objectsystem;

import java.io.IOException;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

public class TestObjectSystem {

  public void run(String filename) throws PyException, IOException {
    JPVM jpvm = new JPVM(filename);
    jpvm.run();
  }

  @Test
  public void test__new__() throws PyException, IOException {
    run("src/test/resources/objectsystem/__pycache__/testnew.cpython-38.pyc");
  }

  @Test
  public void test__new__free() throws PyException, IOException {
    run("src/test/resources/objectsystem/__pycache__/test__new__free.cpython-38.pyc");
  }

  @Test
  public void testComplexExample() throws PyException, IOException {
    run("src/test/resources/objectsystem/__pycache__/test_complex_demo.cpython-38.pyc");
  }

  @Test
  public void testArgs() throws PyException, IOException {
    run("src/test/resources/objectsystem/__pycache__/testargs.cpython-38.pyc");
  }
}
