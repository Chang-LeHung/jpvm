package org.jpvm.testvm;

import java.io.IOException;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;

public class TestMagicFunc {

  @Test
  public void testMagicFunc() {
    String filename = "src/test/resources/testpy/__pycache__/testMagicFunc.cpython-38.pyc";
    try {
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testMagicFunc1() {
    String filename = "src/test/resources/testpy/__pycache__/testOtherMagic.cpython-38.pyc";
    try {
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
