package org.jpvm.testvm;

import java.io.IOException;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.vm.EvaluationLoop;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.junit.Test;

public class TestHelloWorld {

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
  public void testHelloWorld() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test01.cpython-38.pyc";
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
  public void testMultiply() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test05.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("return val = " + ret.str().getData());
  }

  @Test
  public void testIntRepr() {
    PyLongObject object = new PyLongObject(1);
    System.out.println(object.str().getData());
  }

  @Test
  public void test03() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test03.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("return val = " + ret.str().getData());
  }

  @Test
  public void test02() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test02.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("return val = " + ret.str().getData());
  }
}
