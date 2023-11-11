package org.jpvm.excptions.types;

import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.python.BuiltIn;

public class PyPythonBaseExceptionType extends PyTypeType {

  public PyPythonBaseExceptionType() {
    super(PyPythonException.class);
    name = "BaseException";
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyPythonException res;
    if (args.size() == 0) res = new PyPythonException(this, new PyUnicodeObject(name));
    else res = new PyPythonException(this, (PyUnicodeObject) args.get(0));
    return res;
  }

  public PyPythonException call(String message) throws PyException {
    if (message == null) message = "";
    return new PyPythonException(this, new PyUnicodeObject(message));
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    switch (op) {
      case PyCmp_EXC_MATCH -> {
        PyTupleObject typeMro = getMro();
        for (int i = 0; i < typeMro.size(); i++) {
          if (typeMro.get(i) == o) return BuiltIn.True;
        }
        return BuiltIn.False;
      }
      case PyCmp_IS -> {
        if (o == this) return BuiltIn.True;
        return BuiltIn.False;
      }
      case PyCmp_IS_NOT -> {
        if (o == this) return BuiltIn.False;
        return BuiltIn.True;
      }
    }
    return (PyBoolObject)
        PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "Unsupported Operator " + op);
  }
}
