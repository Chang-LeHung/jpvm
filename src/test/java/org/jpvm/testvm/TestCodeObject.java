package org.jpvm.testvm;

import java.io.FileInputStream;
import java.io.IOException;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.module.Disassembler;
import org.jpvm.module.Marshal;
import org.jpvm.pycParser.PyCodeObject;
import org.junit.Test;

public class TestCodeObject {

  @Test
  public void testClosure() throws IOException, PyException {
    String filename = "src/test/resources/closure/__pycache__/test01.cpython-38.pyc";
    FileInputStream stream = new FileInputStream(filename);
    stream.skipNBytes(16);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    System.out.println(pyCodeObject);
    Disassembler disassembler = new Disassembler(pyCodeObject);
    disassembler.dis();
    stream.close();
  }
}
