package org.jpvm.testvm;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class TestImport {

  @Test
  public void testImport() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test04.cpython-38.pyc";
    new PVM(filename).run();
  }

  @Test
  public void testImport02() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test05.cpython-38.pyc";
    new PVM(filename).run();
  }

  @Test
  public void testImport03() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/test07.cpython-38.pyc";
    new PVM(filename).run();
  }

  @Test
  public void testJsoup(){
    try {
      Document document = Jsoup.connect("http://www.baidu.com").get();
      System.out.println(document.title());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testreq() throws PyException, IOException {
    String filename = "src/test/resources/obsy/__pycache__/testreq.cpython-38.pyc";
    new PVM(filename).run();
  }
}
