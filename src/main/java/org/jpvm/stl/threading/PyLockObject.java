package org.jpvm.stl.threading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.python.BuiltIn;

public class PyLockObject extends PyObject {
  public static PyLockObjectType type = PyLockObjectType.getInstance();

  private final ReentrantLock lock;

  public PyLockObject() {
    lock = new ReentrantLock();
  }

  @Override
  public String toString() {
    return "lock" + super.toString();
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @PyClassMethod
  public PyObject acquire(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyObject timeout = null;
    if (kwArgs != null) timeout = kwArgs.get(new PyUnicodeObject("timeout"));
    if (timeout == null) {
      lock.lock();
      return BuiltIn.None;
    } else {
      if (timeout instanceof PyLongObject l) {
        long data = l.getData();
        try {
          if (lock.tryLock(data, TimeUnit.MILLISECONDS)) return BuiltIn.True;
          return BuiltIn.False;
        } catch (InterruptedException ignore) {
        }
      }
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "acquire() requires a single integer argument");
  }

  @PyClassMethod
  public PyObject release(PyTupleObject args, PyDictObject kwArgs) {
    lock.unlock();
    return this;
  }
}
