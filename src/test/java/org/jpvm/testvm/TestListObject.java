package org.jpvm.testvm;

import java.io.IOException;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.vm.EvaluationLoop;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.junit.Test;

public class TestListObject {

  public PyObject test(String filename) throws IOException, PyException {
    PycReader reader = new PycReader(filename);
    reader.doParse();
    PyCodeObject codeObject = reader.getCodeObject();
    BuiltIn.doInit();
    PyDictObject globals = new PyDictObject();
    PyFrameObject object = new PyFrameObject(codeObject, BuiltIn.dict, globals, globals, null);
    EvaluationLoop evaluationLoop = new EvaluationLoop(object);
    return evaluationLoop.pyEvalFrame();
  }

  @Test
  public void testItr() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test14.cpython-38.pyc";
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
  public void testUnpack() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test15.cpython-38.pyc";
    PyObject ret = null;
    try {
      ret = test(filename);
    } catch (PyException e) {
      e.printStackTrace();
    }
    assert ret != null;
    System.out.println("ret = " + ret.str().getData());
  }
}
