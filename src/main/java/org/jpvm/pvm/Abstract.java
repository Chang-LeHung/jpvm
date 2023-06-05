package org.jpvm.pvm;

import org.jpvm.errors.*;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.python.BuiltIn;

import java.util.HashMap;
import java.util.Map;


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

  public static PyObject sub(PyObject v, PyObject w) {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      if (v.getType() != w.getType())
        return BuiltIn.notImplemented;
      try {
        return nv.sub(w);
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
    } else if (callable instanceof PyTypeType t) {
      return t.call(self, args, kwArgs);
    } else if (callable instanceof PyFunctionObject func) {
      if (kwArgs == null)
        kwArgs = new PyDictObject();
      PyCodeObject code = (PyCodeObject) func.getFuncCode();
      var defaults = (PyTupleObject) func.getFuncDefaults();
      var kwDefaults = (PyDictObject) func.getFuncKwDefaults();
      var coVarNames = (PyTupleObject) code.getCoVarNames();
      PyFrameObject f = new PyFrameObject(code, frameObject.getBuiltins(), frameObject.getGlobals(), frameObject);
      // start initialize parameters
      int as = args.size() + kwArgs.size() + defaults.size() + kwDefaults.size();
      if (as != coVarNames.size()) {
        throw new PyParametersError("require " + (coVarNames.size() - defaults.size() + kwDefaults.size()) + " parameters", false);
      }
      for (int i = 0; i < args.size(); i++) {
        f.setLocal(i, args.get(i));
      }
      Map<PyObject, Integer> map = new HashMap<>();
      for (int i = 0; i < coVarNames.size(); i++) {
        map.put(coVarNames.get(i), i);
      }
      kwDefaults.getMap().forEach((x, y) -> f.setLocal(map.get(x), y));
      for (int i = 0; i < defaults.size(); i++) {
        f.setLocal(coVarNames.size() - defaults.size() + i - 1, defaults.get(i));
      }
      // final update passed arguments
      kwArgs.getMap().forEach((x, y) -> f.setLocal(map.get(x), y));
      for (int i = 0; i < coVarNames.size(); i++) {
        if (f.getLocal(i) == null)
          throw new PyParametersError("please pass argument " + coVarNames.get(i).repr(), false);
      }
      f.setBack(frameObject);
      EvaluationLoop eval = new EvaluationLoop();
      return eval.pyEvalFrame(f);
    }
    throw new PyException("call error");
  }

  public static PyObject compare(PyObject w, PyObject v, TypeRichCompare.Operator op) throws PyUnsupportedOperator {
    if (v.getType() != w.getType())
      return BuiltIn.notImplemented;
    return w.richCompare(v, op);
  }
}
