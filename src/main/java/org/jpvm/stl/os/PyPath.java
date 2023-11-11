package org.jpvm.stl.os;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.vm.InterpreterState;
import org.jpvm.vm.JPVM;
import org.jpvm.python.BuiltIn;

public class PyPath extends PyModuleObject {

  public PyPath(PyUnicodeObject moduleName) {
    super(moduleName);
  }

  @PyClassMethod
  public PyObject join(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() != 0) {
      Path path = Paths.get(args.get(0).toString());
      for (int i = 1; i < args.size(); i++) {
        path = path.resolve(args.get(i).toString());
      }
      return new PyUnicodeObject(path.normalize().toString());
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "path.join expected more than 0 arguments, got 0");
  }

  @PyClassMethod
  public PyObject isdir(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      if (Paths.get(args.get(0).toString()).toFile().isDirectory()) return BuiltIn.True;
      return BuiltIn.False;
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "isdir requires 1 argument, but got 0");
  }

  @PyClassMethod
  public PyObject isfile(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      InterpreterState is = JPVM.getThreadState().getIs();
      if (Paths.get(args.get(0).toString()).toFile().isFile()) {
        return BuiltIn.True;
      } else {
        return BuiltIn.False;
      }
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "isdir requires 1 argument, but got 0");
  }
}
