package org.jpvm.stl.threading;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.pvm.*;

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
      InterpreterState is = PVM.getThreadState().getIs();
      PyThreadObject.type.tss.set(owner);
      is.takeGIL();
      PyObject res = Abstract.abstractCall(functionObject, null, args, kwArgs);
      if (res == null) PyErrorUtils.printExceptionInformation();
      is.dropGIL();
      PyThreadObject.type.tss.remove();
    } catch (PyException ignore) {
      PyErrorUtils.printExceptionInformation();
    }
  }
}
