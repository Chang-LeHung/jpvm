package org.jpvm.stl.time;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.pvm.InterpreterState;
import org.jpvm.pvm.PVM;
import org.jpvm.python.BuiltIn;

import java.util.concurrent.TimeUnit;

public class PyModuleMain extends PyModuleObject {
  public PyModuleMain(PyUnicodeObject moduleName) {
    super(moduleName);
  }

  @PyClassMethod
  public PyObject sleep(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyLongObject o) {
        try {
          InterpreterState is = PVM.getThreadState().getIs();
          is.dropGIL();
          TimeUnit.SECONDS.sleep(o.getData());
          is.takeGIL();
          return BuiltIn.None;
        } catch (InterruptedException ignore) {
        }
      } else if (object instanceof PyFloatObject o) {
        InterpreterState is = PVM.getThreadState().getIs();
        is.dropGIL();
        try {
          TimeUnit.MILLISECONDS.sleep((long) o.getData() * 1000);
        } catch (InterruptedException ignore) {
        }
        is.takeGIL();
        return BuiltIn.None;
      }
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "time.sleep only require 1 int argument");
  }
}
