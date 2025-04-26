package org.jpvm.stl.threading;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyConditionObjectType extends PyTypeType {
  private PyConditionObjectType(Class<?> clazz) {
    super(clazz);
  }

  public static final class SelfHolder {
    public static final PyConditionObjectType self =
        new PyConditionObjectType(PyConditionObject.class);
  }

  public static PyConditionObjectType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return new PyConditionObject();
  }
}
