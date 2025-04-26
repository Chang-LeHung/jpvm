package org.jpvm.stl.threading;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyLockObjectType extends PyTypeType {
  private PyLockObjectType(Class<?> clazz) {
    super(clazz);
    name = "lock";
  }

  public static final class SelfHolder {
    public static final PyLockObjectType instance = new PyLockObjectType(PyLockObject.class);
  }

  public static PyLockObjectType getInstance() {
    return SelfHolder.instance;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return new PyLockObject();
  }
}
