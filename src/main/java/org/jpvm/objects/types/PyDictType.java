package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;

public class PyDictType extends PyTypeType {

  public PyDictType() {
    super(PyDictObject.class);
    name = "dict";
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) return new PyDictObject();
    else throw new PyException("dict() takes no arguments");
  }
}
