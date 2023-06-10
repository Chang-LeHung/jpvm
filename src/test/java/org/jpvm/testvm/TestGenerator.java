package org.jpvm.testvm;

import org.jpvm.errors.PyException;
import org.jpvm.module.Disassembler;
import org.jpvm.module.Marshal;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.pvm.EvaluationLoop;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class TestGenerator {

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
  public void testCodeObject() throws IOException, PyException {
    String filename = "src/test/resources/testgen/__pycache__/test01.cpython-38.pyc";
    FileInputStream stream = new FileInputStream(filename);
    stream.skipNBytes(16);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    Disassembler disassembler = new Disassembler(pyCodeObject);
    disassembler.dis();
    stream.close();
  }

  @Test
  public void testGenerator(){
    String filename = "src/test/resources/testgen/__pycache__/test01.cpython-38.pyc";
    try {
      test(filename);
    } catch (IOException | PyException e) {
      e.printStackTrace();
    }
  }
}
