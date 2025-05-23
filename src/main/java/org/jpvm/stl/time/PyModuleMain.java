package org.jpvm.stl.time;

import java.util.concurrent.TimeUnit;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.python.BuiltIn;

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
          TimeUnit.SECONDS.sleep(o.getData());
          return BuiltIn.None;
        } catch (InterruptedException ignore) {
        }
      } else if (object instanceof PyFloatObject o) {
        try {
          long l = (long) (o.getData() * 1000);
          TimeUnit.MILLISECONDS.sleep(l);
        } catch (InterruptedException ignore) {
        }
        return BuiltIn.None;
      }
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "time.sleep only require 1 int argument");
  }

  @PyClassMethod
  public PyObject time(PyTupleObject args, PyDictObject kwArgs) {
    long l = System.currentTimeMillis();
    return new PyFloatObject(l / 1000.0);
  }
}
