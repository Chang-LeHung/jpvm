package org.jpvm.stl.threading;

import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.vm.*;

public class PyCodeExecutor implements Runnable {

  private final PyFunctionObject functionObject;
  private final PyTupleObject args;
  private final PyDictObject kwArgs;
  private final PyThreadObject owner;

  public PyCodeExecutor(
      PyFunctionObject functionObject,
      PyTupleObject args,
      PyDictObject kwArgs,
      PyThreadObject owner) {
    this.functionObject = functionObject;
    this.args = args;
    this.kwArgs = kwArgs;
    this.owner = owner;
  }

  @Override
  public void run() {
    try {
      PyThreadObject.type.tss.set(owner);
      PyObject res = Abstract.abstractCall(functionObject, null, args, kwArgs);
      if (res == null) PyErrorUtils.printExceptionInformation();
      PyThreadObject.type.tss.remove();
    } catch (PyException ignore) {
      PyErrorUtils.printExceptionInformation();
    }
  }
}
