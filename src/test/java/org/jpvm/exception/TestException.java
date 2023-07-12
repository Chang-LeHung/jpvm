package org.jpvm.exception;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

import java.io.IOException;

public class TestException {

  @Test
  public void test01() {
    String file = "src/test/resources/exception/__pycache__/demo01.cpython-38.pyc";
    try {
      PVM pvm = new PVM(file);
      pvm.run();
    } catch (PyException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
