package org.jpvm.pvm;

import java.io.IOException;
import org.jpvm.errors.PyException;

/** jpvm boot from this class */
public class PyMain {

  public static void main(String[] args) throws PyException, IOException {
    if (args.length == 1) {
      PVM pvm = new PVM(args[0]);
      pvm.run();
      pvm.exit();
      return;
    }
    System.err.println("Please input a pyc filename");
  }
}
