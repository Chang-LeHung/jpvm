package org.jpvm.internal;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.protocols.PyNumberMethods;

public class NumberHelper {

  public static Long transformPyObject2Long(PyObject o) {
    if (o instanceof PyNumberMethods n) {
      PyObject object;
      try {
        object = n.nbInt();
        if (object instanceof PyLongObject d)
          return d.getData();
      } catch (PyException ignored) {
      }
      try {
        object = n.index();
        if (object instanceof PyLongObject d)
          return d.getData();
      } catch (PyException ignored) {
      }
    }
    return null;
  }
}
