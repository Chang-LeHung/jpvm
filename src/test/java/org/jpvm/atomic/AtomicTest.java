package org.jpvm.atomic;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

import java.io.IOException;

public class AtomicTest {

  @Test
  public void testAtomic() throws PyException, IOException {
    String filename = "src/test/resources/atomic/__pycache__/atomic.cpython-38.pyc";
    JPVM JPVM = new JPVM(filename);
    JPVM.run();
  }
}
