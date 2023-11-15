package org.jpvm.stl.threading;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyConditionObjectType extends PyTypeType {
  public PyConditionObjectType(Class<?> clazz) {
    super(clazz);
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return new PyConditionObject();
  }
}
