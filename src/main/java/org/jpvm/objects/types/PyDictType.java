package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;

public class PyDictType extends PyTypeType {

  private PyDictType() {
    super(PyDictObject.class);
    name = "dict";
  }

  public static final class SelfHolder {
    public static PyDictType self = new PyDictType();
  }

  public static PyDictType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) return new PyDictObject();
    else throw new PyException("dict() takes no arguments");
  }
}
