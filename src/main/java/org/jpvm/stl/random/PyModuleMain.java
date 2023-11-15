package org.jpvm.stl.random;

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
  public PyObject test(PyTupleObject args, PyDictObject kwArgs) {
    System.out.println("Hello World");
    return BuiltIn.None;
  }

  @PyClassMethod
  public PyObject randint(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      PyObject start = args.get(0);
      PyObject end = args.get(1);
      if (start instanceof PyLongObject s && end instanceof PyLongObject e) {
        return new PyLongObject(s.getData() + (long) (Math.random() * (e.getData() - s.getData())));
      }
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "randint() takes exactly 2 arguments (" + args.size() + " given)");
  }
}
