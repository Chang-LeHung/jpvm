package org.jpvm.testvm;

import java.io.IOException;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.vm.EvaluationLoop;
import org.jpvm.vm.JPVM;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.junit.Test;

public class TestDict {
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
  public void testItemsKeysValues() throws IOException {
    String filename = "src/test/resources/syntax/__pycache__/test16.cpython-38.pyc";
    try {
      JPVM JPVM = new JPVM(filename);
      JPVM.run();
    } catch (PyException e) {
      throw new RuntimeException(e);
    }
  }
}
