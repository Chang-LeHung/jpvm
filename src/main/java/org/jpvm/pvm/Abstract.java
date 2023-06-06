package org.jpvm.pvm;

import org.jpvm.errors.*;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.python.BuiltIn;

import java.util.HashMap;
import java.util.Map;


/**
 * implement a simple version, not implement all program semantics like cpython
 */
public class Abstract {


  public static PyObject multiply(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.mul(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.mul(v);
      } catch (PyException ignore) {
      }
    } else if (v instanceof PySequenceMethods nv && w instanceof PyNumberMethods) {
      try {
        return nv.sqRepeat(w);
      } catch (PyNotImplemented | PyTypeNotMatch ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply mul on " + v.repr() + " and " + w.repr());
  }

  /**
   * whether w is subtype of v or not
   */
  public static boolean isSubType(PyObject v, PyObject w) {
    var type = (PyTypeType) w.getType();
    PyBoolObject o = type.isSubType((PyTypeType) v.getType());
    return o.isTrue();
  }

  public static PyObject add(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.add(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.add(v);
      } catch (PyException ignore) {
      }
    } else if (v instanceof PySequenceMethods nv && w instanceof PySequenceMethods) {
      // like "hello" + "world"
      try {
        return ((PySequenceMethods) v).sqConcat(w);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not add add on " + v.repr() + " and " + w.repr());
  }

  public static PyObject sub(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.sub(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.sub(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply sub on " + v.repr() + " and " + w.repr());
  }

  public static PyObject pow(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.pow(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.pow(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply pow on " + v.repr() + " and " + w.repr());
  }

  public static PyObject lshift(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.lshift(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.lshift(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply lshift on " + v.repr() + " and " + w.repr());
  }

  public static PyObject rshift(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.rshift(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.rshift(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply rshift on " + v.repr() + " and " + w.repr());
  }

  public static PyObject index(PyObject v) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv) {
      try {
        return nv.index();
      } catch (PyNotImplemented ignore) {
        return BuiltIn.notImplemented;
      }
    }
    throw new PyTypeNotMatch("can not apply indx on " + v.repr());
  }

  public static PyObject abs(PyObject v) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv) {
      try {
        return nv.abs();
      } catch (PyNotImplemented ignore) {
        return BuiltIn.notImplemented;
      }
    }
    throw new PyTypeNotMatch("can not apply abs on " + v.repr());
  }

  public static PyObject and(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.and(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.and(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply and on " + v.repr() + " and " + w.repr());
  }

  public static PyObject divmod(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.divmod(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.divmod(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply divmod on " + v.repr() + " and " + w.repr());
  }

  public static PyObject floorDiv(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.floorDiv(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.floorDiv(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply floorDiv on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceAdd(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceAdd(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceAdd(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceAdd on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceAnd(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceAnd(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceAnd(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceAnd on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceFloorDiv(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceFloorDiv(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceFloorDiv(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceFloorDiv on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceMatrixMul(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceMatrixMul(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceMatrixMul(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceMatrixMul on " + v.repr() + " and " + w.repr());
  }

  public static PyObject trueDiv(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.trueDiv(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.trueDiv(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply trueDiv on " + v.repr() + " and " + w.repr());
  }

  public static PyObject matrixMul(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.matrixMul(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.matrixMul(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply matrixMul on " + v.repr() + " and " + w.repr());
  }

  public static PyObject mod(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.mod(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.mod(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply mod on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceMul(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceMul(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceMul(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceMul on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceOr(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceOr(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceOr(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceOr on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplacePow(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplacePow(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplacePow(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplacePow on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceRshift(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceRshift(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceRshift(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceRshift on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceLshift(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceLshift(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceLshift(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceLshift on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceSub(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceSub(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceSub(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceSub on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceTrue(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceTrueDiv(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceTrueDiv(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceTrue on " + v.repr() + " and " + w.repr());
  }

  public static PyObject or(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.or(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.or(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply or on " + v.repr() + " and " + w.repr());
  }

  public static PyObject xor(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.xor(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.xor(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply xor on " + v.repr() + " and " + w.repr());
  }

  public static PyObject inplaceXor(PyObject v, PyObject w) throws PyTypeNotMatch {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.inplaceXor(w);
      } catch (PyException ignore) {
      }
      try {
        return nw.inplaceXor(v);
      } catch (PyException ignore) {
      }
    }
    throw new PyTypeNotMatch("can not apply inplaceXor on " + v.repr() + " and " + w.repr());
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
      // just for debugging to avoid cycle reference, idea will get stuck for toString method
      PyDictObject globals = new PyDictObject();
      globals.addAll(frameObject.getGlobals());
      int argSize = code.getCoKwOnlyArCnt() + code.getCoPosOnlyArCnt() + code.getCoArgument();
      // use below in release version
//      PyFrameObject f = new PyFrameObject(code, frameObject.getBuiltins(), frameObject.getGlobals(), frameObject);
      PyFrameObject f = new PyFrameObject(code, frameObject.getBuiltins(), globals, frameObject);
      // start initialize parameters
      for (int i = 0; i < args.size(); i++) {
        f.setLocal(i, args.get(i));
      }
      Map<PyObject, Integer> map = new HashMap<>();
      for (int i = 0; i < coVarNames.size(); i++) {
        map.put(coVarNames.get(i), i);
      }
      kwDefaults.getMap().forEach((x, y) -> f.setLocal(map.get(x), y));
      for (int i = 0; i < defaults.size(); i++) {
        f.setLocal(argSize - defaults.size() + i - 1, defaults.get(i));
      }
      // final update passed arguments
      kwArgs.getMap().forEach((x, y) -> f.setLocal(map.get(x), y));
      for (int i = 0; i < argSize; i++) {
        if (f.getLocal(i) == null)
          throw new PyParametersError("please pass argument " + coVarNames.get(i).repr(), false);
      }
      f.setBack(frameObject);
      EvaluationLoop eval = new EvaluationLoop();
      return eval.pyEvalFrame(f);
    }
    throw new PyException("abstract call error occurred");
  }

  public static PyObject compare(PyObject w, PyObject v, TypeRichCompare.Operator op) throws PyUnsupportedOperator {
    if (v.getType() != w.getType())
      return BuiltIn.notImplemented;
    return w.richCompare(v, op);
  }

  public static PyBoolObject isTrue(PyObject o) throws PyTypeError {
    if (o == BuiltIn.True) return BuiltIn.True;
    if (o == BuiltIn.False) return BuiltIn.False;
    if (o == BuiltIn.None) return BuiltIn.False;
    if (o instanceof PyNumberMethods num) {
      try {
        return (PyBoolObject) num.bool();
      } catch (PyNotImplemented ignore) {
      }
    } else if (o instanceof PySequenceMethods seq) {
      try {
        return isTrue(seq.sqLength(null));
      } catch (PyNotImplemented ignore) {
      }
    } else if (o instanceof PyMappingMethods map) {
      try {
        return isTrue(map.mpLength(null));
      } catch (PyNotImplemented ignore) {
      }
    }
    throw new PyTypeError(o.repr() + "can not be seen as a bool object");
  }

  public static PyObject getItem(PyObject v, PyObject w) throws PyTypeError {
    if (v instanceof PySequenceMethods seq) {
      try {
        return seq.sqItem(w);
      } catch (PyTypeNotMatch | PyNotImplemented ignore) {
      }
    } else if (v instanceof PyMappingMethods map) {
      try {
        return map.mpSubscript(w);
      } catch (PyException ignore) {
      }
    }
    // get item from object `dict` to be implemented
    throw new PyTypeError("can not get " + w.repr() + " from " + v.repr());
  }

}
