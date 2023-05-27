package org.jpvm.internal;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.protocols.PyNumberMethods;

public class NumberHelper {

  public static Long transformPyObject2Long (PyObject o) {
    if (o instanceof PyNumberMethods n) {
      PyObject object;
      try {
        object = n.nbInt();
        if (object instanceof PyLongObject d)
          return d.getData();
      } catch (PyNotImplemented ignored) {
      }
      try {
        object = n.index();
        if (object instanceof PyLongObject d)
          return d.getData();
      } catch (PyNotImplemented ignored) {
      }
    }
    return null;
  }
}
