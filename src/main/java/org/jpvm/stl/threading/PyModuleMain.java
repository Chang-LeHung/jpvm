package org.jpvm.stl.threading;

import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;

public class PyModuleMain extends PyModuleObject {
  @PyClassAttribute
  private final PyThreadObjectType Thread = PyThreadObject.type;

  @PyClassAttribute
  private final PyLockObjectType Lock = PyLockObject.type;

  @PyClassAttribute
  private final PyLockObjectType RLock = PyLockObject.type;

  @PyClassAttribute
  private final PyConditionObjectType Condition = PyConditionObject.type;

  public PyModuleMain(PyUnicodeObject moduleName) {
    super(moduleName);
  }

  @PyClassMethod
  public PyObject current_thread(PyTupleObject args, PyDictObject kwArgs) {
    return Thread.current_thread(args, kwArgs);
  }
}
