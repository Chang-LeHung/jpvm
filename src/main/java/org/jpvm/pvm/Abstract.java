package org.jpvm.pvm;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyObject;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.python.BuiltIn;


/**
 * implement a simple version, not implement all program semantics like cpython
 */
public class Abstract {


  public static PyObject multiply(PyObject v, PyObject w) {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      if (v.getType() != w.getType())
        return BuiltIn.notImplemented;
      try {
        return nv.mul(w);
      } catch (PyNotImplemented | PyTypeNotMatch ignore) {
        return BuiltIn.notImplemented;
      }
    }
    return BuiltIn.notImplemented;
  }

  public static PyObject add(PyObject v, PyObject w) {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      if (v.getType() != w.getType())
        return BuiltIn.notImplemented;
      try {
        return nv.add(w);
      } catch (PyNotImplemented | PyTypeNotMatch ignore) {
        return BuiltIn.notImplemented;
      }
    }
    return BuiltIn.notImplemented;
  }
}
