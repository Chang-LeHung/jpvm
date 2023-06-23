package org.jpvm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

public class TestPvm {

  public static List<String> scanFiles(String directory) {
    File dir = new File(directory);
    var res = new ArrayList<String>();
    File[] files = dir.listFiles();

    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          res.addAll(scanFiles(file.getAbsolutePath()));
        } else {
          String fileName = file.getName();
          if (fileName.endsWith(".pyc")) {
            res.add(file.getAbsolutePath());
          }
        }
      }
    }
    return res;
  }

  @Test
  public void testPVM() {
    String filename = "src/test/resources/pys/__pycache__/add.cpython-38.pyc";
    try {
      new PVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testStringConcat() {
    String filename = "src/test/resources/syntax/__pycache__/test07.cpython-38.pyc";
    try {
      new PVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testAllPyc() {
    List<String> files = scanFiles("src/test/resources/");
    for (String file : files) {
      if (file.contains("dis") || file.contains("pycparser"))
        continue;
      try {
        System.out.println("testing " + file + "...");
        new PVM(file).run();
      } catch (PyException | IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
