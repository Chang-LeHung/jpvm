package org.jpvm.excptions.types;

import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTypeType;

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
}
