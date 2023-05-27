package org.jpvm.pvm;

import org.jpvm.objects.PyFrameObject;
import org.jpvm.pycParser.PyCodeObject;

public class EvaluationLoop {

  private final PyFrameObject frameObject;
  private final PyCodeObject codeObject;

  public EvaluationLoop(PyCodeObject codeObject) {
    this.codeObject = codeObject;
    frameObject = new PyFrameObject(
        null,
        codeObject,
        null,
        null,
        null
    );
  }
}
