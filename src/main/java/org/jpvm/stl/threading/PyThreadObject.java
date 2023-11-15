package org.jpvm.stl.threading;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.python.BuiltIn;

public class PyThreadObject extends PyObject {

  public static PyThreadObjectType type = new PyThreadObjectType(PyThreadObject.class);
  private final PyTupleObject args;
  private final PyUnicodeObject name;
  private final PyFunctionObject target;
  private THREAD_STATE state;
  private Thread thread;
  private boolean daemon;

  public PyThreadObject(PyTupleObject args, PyUnicodeObject name, PyFunctionObject target) {
    this.args = args;
    this.name = name;
    this.target = target;
    state = THREAD_STATE.UNINITIALIZED;
  }

  public boolean getDaemon() {
    return daemon;
  }

  public void setDaemon(boolean daemon) {
    this.daemon = daemon;
  }

  @PyClassMethod
  public PyObject setDaemon(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (state == THREAD_STATE.UNINITIALIZED)
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "only a not stated thread can call this method");
    if (args.size() == 1) {
      try {
        PyObject object = args.get(0);
        if (object == BuiltIn.True) {
          daemon = true;
          return BuiltIn.None;
        } else if (object == BuiltIn.False) {
          daemon = false;
          return BuiltIn.None;
        }
        PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "setDaemon require 1 bool argument");
      } catch (PyException ignore) {
      }
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "setDaemon require 1 bool argument");
  }

  @PyClassMethod
  public PyObject start(PyTupleObject args, PyDictObject kwArgs) {
    thread = new Thread(new PyCodeExecutor(target, this.args, kwArgs, this));
    thread.setDaemon(daemon);
    state = THREAD_STATE.STARTED;
    thread.start();
    state = THREAD_STATE.FINISHED;
    return BuiltIn.None;
  }

  @PyClassMethod
  public PyObject join(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (state == THREAD_STATE.UNINITIALIZED)
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "only a stated thread can call this method");
    try {
      // deal lock warning
      thread.join();
    } catch (InterruptedException ignore) {
    }
    return BuiltIn.None;
  }

  @Override
  public String toString() {
    return "<Thread (" + name + ")>";
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

  public THREAD_STATE getState() {
    return state;
  }

  public void setState(THREAD_STATE state) {
    this.state = state;
  }

  enum THREAD_STATE {
    UNINITIALIZED,
    STARTED,
    FINISHED,
  }
}
