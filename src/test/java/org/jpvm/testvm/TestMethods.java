package org.jpvm.testvm;

import java.io.*;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.vm.EvaluationLoop;
import org.jpvm.vm.JPVM;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.junit.Test;

public class TestMethods {
  public PyObject test(String filename) throws IOException, PyException {
    PycReader reader = new PycReader(filename);
    reader.doParse();
    PyCodeObject codeObject = reader.getCodeObject();
    BuiltIn.doInit();
    PyFrameObject object = new PyFrameObject(codeObject, BuiltIn.dict, null);
    EvaluationLoop evaluationLoop = new EvaluationLoop(object);
    return evaluationLoop.pyEvalFrame();
  }

  @Test
  public void testTupleMethods() throws IOException {
    String filename = "src/test/resources/testpy/__pycache__/test01.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("ret = " + ret.str().getData());
  }

  @Test
  public void testAllAny() throws IOException {
    String filename = "src/test/resources/testpy/__pycache__/test02.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("ret = " + ret.str().getData());
  }

  @Test
  public void testList() throws IOException {
    String filename = "src/test/resources/testpy/__pycache__/testlist.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("ret = " + ret.str().getData());
  }

  @Test
  public void testLongOther() {
    String filename = "src/test/resources/testpy/__pycache__/testOtherLong.cpython-38.pyc";
    try {
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testOpen() {
    String filename = "src/test/resources/testpy/__pycache__/testopen.cpython-38.pyc";
    try {
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testRWBfile() {
    String filename = "src/test/resources/testpy/__pycache__/testrwbfile.cpython-38.pyc";
    try {
      new JPVM(filename).run();
    } catch (PyException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testBr() throws IOException {
    String path = "src/test/resources/testpy/test.txt";
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(path));
      bw.write("Hello, this is a test!\n");
      bw.write("This is the second line.\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
