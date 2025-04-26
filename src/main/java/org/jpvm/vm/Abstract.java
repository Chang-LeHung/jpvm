package org.jpvm.vm;

import java.util.HashMap;
import java.util.Map;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.module.Marshal;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.python.BuiltIn;

/** implement a simple version, not implement all program semantics like cpython */
public class Abstract {

  public static PyObject multiply(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.mul(w);
      } catch (PyException ignore) {
        PyErrorUtils.cleanThreadException();
      }
      return nw.mul(v);
    } else if (v instanceof PyNumberMethods && w instanceof PySequenceMethods nw) {
      return nw.sqRepeat(v);
    } else if (v instanceof PySequenceMethods nv && w instanceof PyNumberMethods) {
      return nv.sqRepeat(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply mul on " + v.repr() + " and " + w.repr());
    return null;
  }

  /** whether w is subtype of v or not */
  public static boolean isSubType(PyObject v, PyObject w) throws PyException {
    var type = (PyTypeType) w.getType();
    PyBoolObject o = type.isSubType(v.getType());
    return o.isTrue();
  }

  public static PyObject add(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.add(w);
      } catch (PyException ignore) {
        PyErrorUtils.cleanThreadException();
      }
      return nw.add(v);
    } else if (v instanceof PySequenceMethods && w instanceof PySequenceMethods) {
      // like "hello" + "world"
      return ((PySequenceMethods) v).sqConcat(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not add add on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject sub(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.sub(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply sub on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject pow(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.pow(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply pow on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject lshift(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.lshift(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply lshift on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject rshift(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.rshift(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply rshift on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject index(PyObject v) throws PyException {
    if (v instanceof PyNumberMethods nv) {
      return nv.index();
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "can not index pow on " + v.repr());
    return null;
  }

  public static PyObject abs(PyObject v) throws PyException {
    if (v instanceof PyNumberMethods nv) {
      return nv.abs();
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "can not apply abs on " + v.repr());
    return null;
  }

  public static PyObject and(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.and(w);
      } catch (PyException ignore) {
        PyErrorUtils.cleanThreadException();
      }
      return nw.and(v);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply and on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject divmod(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.divmod(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply divmod on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject floorDiv(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.floorDiv(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply floorDiv on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceAdd(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceAdd(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceAdd on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceMod(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceMod(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceAdd on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceTrueDiv(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceTrueDiv(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceTrueDiv on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceAnd(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceAnd(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceAnd on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceFloorDiv(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceFloorDiv(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceFloorDiv on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceMatrixMul(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceMatrixMul(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceMatrixMul on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject trueDiv(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.trueDiv(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply trueDiv on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject matrixMul(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.matrixMul(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply matrixMul on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject mod(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv) {
      return nv.mod(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply mod on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceMul(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceMul(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceMul on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceOr(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceOr(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceOr on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplacePow(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplacePow(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplacePow on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceRshift(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceRshift(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceRshift on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceLshift(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceLshift(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceLshift on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceSub(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceSub(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceSub on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceTrue(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceTrueDiv(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceTrue on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject or(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.or(w);
      } catch (PyException ignore) {
        PyErrorUtils.cleanThreadException();
      }
      return nw.or(v);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply or on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject xor(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods nw) {
      try {
        return nv.xor(w);
      } catch (PyException ignore) {
        PyErrorUtils.cleanThreadException();
      }
      return nw.xor(v);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply xor on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject inplaceXor(PyObject v, PyObject w) throws PyException {
    if (v instanceof PyNumberMethods nv && w instanceof PyNumberMethods) {
      return nv.inplaceXor(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not apply inplaceXor on " + v.repr() + " and " + w.repr());
    return null;
  }

  public static PyObject abstractCall(PyObject callable, PyObject self, PyTupleObject args,
      PyDictObject kwArgs) throws PyException {
    return abstractCall(callable, self, args, kwArgs, null);
  }

  public static PyObject abstractCall(PyObject callable, PyObject self, PyTupleObject args,
      PyDictObject kwArgs, PyFrameObject frameObject) throws PyException {
    return abstractCall(callable, self, args, kwArgs, frameObject, null);
  }

  public static PyObject abstractCall(PyObject callable, PyObject self, PyTupleObject args,
      PyDictObject kwArgs, PyFrameObject frameObject, PyDictObject locals) throws PyException {
    if (callable instanceof PyNativeMethodObject nativeMethodObject) {
      PyTupleObject newArgs = new PyTupleObject(args.size() + 1);
      newArgs.set(0, self);
      for (int i = 0; i < args.size(); i++) {
        newArgs.set(i + 1, args.get(i));
      }
      return (nativeMethodObject.call(newArgs, kwArgs));
    } else if (callable instanceof PyTypeType t) {
      return t.call(args, kwArgs);
    } else if (callable instanceof PyMethodObject) {
      return callable.call(args, kwArgs);
    } else {
      if (callable instanceof PyFunctionObject func) {
        if (kwArgs == null)
          kwArgs = new PyDictObject();
        if (args == null)
          args = PyTupleObject.zero;
        PyCodeObject code = (PyCodeObject) func.getFuncCode();
        var defaults = (PyTupleObject) func.getFuncDefaults();
        var kwDefaults = (PyDictObject) func.getFuncKwDefaults();
        var coVarNames = (PyTupleObject) code.getCoVarNames();
        PyDictObject globals = (PyDictObject) func.getFuncGlobals();
        int argSize = code.getCoKwOnlyArCnt() + code.getCoArgument();
        if (locals == null)
          locals = new PyDictObject();
        PyFrameObject f = new PyFrameObject(func, code, BuiltIn.dict, globals, locals, frameObject);
        Map<PyObject, Integer> map = new HashMap<>();
        for (int i = 0; i < coVarNames.size(); i++) {
          map.put(coVarNames.get(i), i);
        }
        PyDictObject kwDict = null;
        int i;
        if ((code.getCoFlags() & Marshal.CO_VARKEYWORDS) != 0) {
          kwDict = new PyDictObject();
          i = argSize;
          if ((code.getCoFlags() * Marshal.CO_VARARGS) != 0)
            i++;
          f.setLocal(i, kwDict);
        }
        int n;
        n = Math.min(args.size(), code.getCoArgument());
        for (int j = 0; j < n; j++) {
          f.setLocal(j, args.get(j));
        }
        if ((code.getCoFlags() & Marshal.CO_VARARGS) != 0) {
          PyTupleObject starArgs = new PyTupleObject(args.size() - n);
          for (int j = n; j < args.size(); j++) {
            starArgs.set(j - n, args.get(j));
          }
          f.setLocal(argSize, starArgs);
        }
        TypeDoIterate iterator = kwArgs.getIterator();
        while (iterator.hasNext()) {
          PyObject key = iterator.next();
          PyObject val = kwArgs.get(key);
          if (map.get(key) == null) {
            if (kwDict == null)
              return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
                  "unexpected keyword argument " + key);
            else
              kwDict.put(key, val);
          } else {
            if (f.getLocal(map.get(key)) != null) {
              return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
                  "duplicate argument " + key);
            }
            f.setLocal(map.get(key), val);
          }
        }
        if (args.size() > code.getCoArgument() && (code.getCoFlags() & Marshal.CO_VARARGS) == 0) {
          return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "too many arguments");
        }
        if (args.size() < code.getCoArgument()) {
          int m = code.getCoArgument() - defaults.size();
          int missing = 0;
          for (i = args.size(); i < m; i++) {
            if (f.getLocal(i) == null) {
              missing++;
            }
          }
          if (missing > 0) {
            return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
                "missing " + missing + " arguments");
          }
          if (n > m)
            i = n - m;
          else
            i = 0;
          for (; i < defaults.size(); i++) {
            if (f.getLocal(m + i) == null) {
              f.setLocal(m + i, defaults.get(i));
            }
          }
        }
        if (kwDefaults.size() > 0) {
          PyObject name;
          for (i = code.getCoArgument(); i < argSize; i++) {
            if (f.getLocal(i) != null) {
              continue;
            }
            name = ((PyTupleObject) code.getCoVarNames()).get(i);
            PyObject val = kwDefaults.get(name);
            if (val != null) {
              f.setLocal(i, kwDefaults.get(name));
            } else {
              return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
                  "missing keyword only argument " + name);
            }
          }
        }
        var coCellVars = (PyTupleObject) code.getCoCellVars();
        for (i = 0; i < coCellVars.size(); i++) {
          PyObject key = coCellVars.get(i);
          Integer idx = map.get(key);
          if (idx != null) {
            PyObject local = f.getLocal(idx);
            f.setFreeVars(i, local);
          }
        }

        if (((PyCodeObject) (func.getFuncCode())).isGenerator())
          return new PyGeneratorObject(f);
        EvaluationLoop eval = new EvaluationLoop(f);
        JPVM.getThreadState().increaseRecursionDepth();
        ThreadState ts = JPVM.getThreadState();
        // store current frame
        PyFrameObject cf = ts.getCurrentFrame();
        ts.setCurrentFrame(f);
        if (ts.isOverFlow())
          PyErrorUtils.pyErrorFormat(PyErrorUtils.StackOverflowError, "recursion depth exceeded");
        PyObject res = eval.pyEvalFrame();
        JPVM.getThreadState().decreaseRecursionDepth();
        // restore current frame
        ts.setCurrentFrame(cf);
        return res;
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "abstract call error occurred");
    return null;
  }

  public static PyObject compare(PyObject w, PyObject v, TypeRichCompare.Operator op)
      throws PyException {
    return w.richCompare(v, op);
  }

  public static PyBoolObject isTrue(PyObject o) throws PyException {
    if (o == BuiltIn.True)
      return BuiltIn.True;
    if (o == BuiltIn.False)
      return BuiltIn.False;
    if (o == BuiltIn.None)
      return BuiltIn.False;
    if (o instanceof PyNumberMethods num) {
      return (PyBoolObject) num.bool();
    } else if (o instanceof PySequenceMethods seq) {
      return isTrue(seq.sqLength());
    } else if (o instanceof PyMappingMethods map) {
      return isTrue(map.mpLength());
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        o.repr() + "can not be seen as a bool object");
    return null;
  }

  public static PyObject getItem(PyObject v, PyObject w) throws PyException {
    if (v instanceof PySequenceMethods seq) {
      try {
        return seq.sqItem(w);
      } catch (Exception ignore) {
        PyErrorUtils.cleanThreadException();
      }
    }
    if (v instanceof PyMappingMethods map) {
      return map.mpSubscript(w);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "can not get " + w.repr() + " from " + v.repr());
    // get item from object `dict` to be implemented
    return null;
  }

  public static void assignItem(PyObject obj, PyObject key, PyObject val) throws PyException {
    PyException error = null;
    if (obj instanceof PySequenceMethods seq) {
      try {
        seq.sqAssItem(key, val);
        return;
      } catch (PyException e) {
        error = e;
        PyErrorUtils.cleanThreadException();
      }
    }

    if (obj instanceof PyMappingMethods map) {
      map.mpAssSubscript(key, val);
      return;
    }
    if (null != error) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, error.getMessage());
    }
  }
}
