package org.jpvm.exceptions.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.pyobjs.PyExceptionObject;
import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.python.BuiltIn;

public class PyBaseExceptionType extends PyTypeType {

  public PyBaseExceptionType() {
    super(PyExceptionObject.class);
    name = "BaseException";
  }

  @Override
  public PyExceptionObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyExceptionObject val;
    if (args.size() == 0) val = new PyExceptionObject(new PyUnicodeObject(name));
    else val = new PyExceptionObject((PyUnicodeObject) args.get(0));
    val.setType(PyBaseExceptionType.type);
    return val;
  }

  public PyUnicodeObject extractPyUnicodeObjectFromArgs(PyTupleObject args) throws PyException {
    if (args.size() == 0) return new PyUnicodeObject(name);
    return (PyUnicodeObject) args.get(0);
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
