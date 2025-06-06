package org.jpvm.stl.threading;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;

public class PyThreadObjectType extends PyTypeType {

  public ThreadLocal<PyThreadObject> tss = new ThreadLocal<>();

  private int threadNum;

  private PyThreadObjectType(Class<?> clazz) {
    super(clazz);
    threadNum = 0;
    name = "Thread";
  }

  public static final class SelfHolder {
    public static final PyThreadObjectType self = new PyThreadObjectType(PyThreadObject.class);
  }

  public static PyThreadObjectType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (kwArgs != null) {
      PyObject target = kwArgs.get(new PyUnicodeObject("target"));
      PyObject name = kwArgs.get(new PyUnicodeObject("name"));
      if (name == null) {
        name = new PyUnicodeObject("Thread-" + ++threadNum);
      }
      PyObject funcArgs = kwArgs.get(new PyUnicodeObject("args"));
      if (target instanceof PyFunctionObject functionObject) {
        return new PyThreadObject((PyTupleObject) funcArgs, (PyUnicodeObject) name, functionObject);
      }
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "Thread at require at least 1 function as argument");
  }

  public PyObject current_thread(PyTupleObject args, PyDictObject kwArgs) {
    PyThreadObject t = tss.get();
    if (t == null) {
      // must be main thread
      PyThreadObject thread = new PyThreadObject(null, new PyUnicodeObject("MainThread"), null);
      thread.setState(PyThreadObject.THREAD_STATE.STARTED);
      tss.set(thread);
      return thread;
    }
    return t;
  }
}
