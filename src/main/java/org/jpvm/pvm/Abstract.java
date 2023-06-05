package org.jpvm.pvm;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyTypeType;
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

  public static PyObject abstractCall(PyObject callable, PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return abstractCall(callable, self, args, kwArgs, null);
  }

  public static PyObject abstractCall(PyObject callable, PyObject self, PyTupleObject args,
                                      PyDictObject kwArgs, PyFrameObject frameObject) throws PyException {
    if (callable instanceof PyNativeMethodObject nativeMethodObject) {
      return (nativeMethodObject.call(self, args, kwArgs));
    }else if (callable instanceof PyTypeType t) {
      return t.call(self, args, kwArgs);
    }else if (callable instanceof PyFunctionObject) {

    }
    throw new PyException("call error");
  }

  public static PyObject compare(PyObject w, PyObject v, TypeRichCompare.Operator op) throws PyUnsupportedOperator {
    if (v.getType() != w.getType())
      return BuiltIn.notImplemented;
    return w.richCompare(v, op);
  }
}
