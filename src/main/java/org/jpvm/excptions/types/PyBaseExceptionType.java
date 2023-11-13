package org.jpvm.excptions.types;

import org.jpvm.excptions.PyExceptionContext;
import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.python.BuiltIn;

public class PyBaseExceptionType extends PyTypeType {

  public PyBaseExceptionType() {
    super(PyExceptionContext.class);
    name = "BaseException";
  }

  @Override
  public PyExceptionContext call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyExceptionContext res;
    if (args.size() == 0) res = new PyExceptionContext(this, new PyUnicodeObject(name));
    else res = new PyExceptionContext(this, (PyUnicodeObject) args.get(0));
    return res;
  }

  public PyObject call(String msg) throws PyException {
    PyUnicodeObject message = new PyUnicodeObject(msg);
    PyTupleObject args = new PyTupleObject(1);
    args.set(0, message);
    return call(args, null);
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    switch (op) {
      case PyCmp_EXC_MATCH -> {
        PyListObject typeMro = getMro();
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
