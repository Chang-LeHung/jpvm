package org.jpvm.magicmethod;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

import java.io.IOException;

public class MagicMethod {

  public void run(String filename) throws PyException, IOException {
    JPVM jpvm = new JPVM(filename);
    jpvm.run();
  }

  @Test
  public void testStrRepr() throws PyException, IOException {
    run("src/test/resources/magicmethod/__pycache__/str_repr.cpython-38.pyc");
  }

  @Test
  public void testMro() throws PyException, IOException {
    run("src/test/resources/magicmethod/__pycache__/mro.cpython-38.pyc");
  }
}
