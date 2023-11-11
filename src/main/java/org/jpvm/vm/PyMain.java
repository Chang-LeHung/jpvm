package org.jpvm.vm;

import java.io.IOException;
import org.jpvm.errors.PyException;

/** jpvm boots from this class */
public class PyMain {

  public static void main(String[] args) throws PyException, IOException {
    if (args.length == 1) {
      JPVM JPVM = new JPVM(args[0]);
      JPVM.run();
      JPVM.exit();
      return;
    }
    System.err.println("Please input a pyc filename");
  }
}
