package org.jpvm.stl;

import org.apache.commons.math3.special.Erf;
import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.protocols.PyNumberMethods;

public class math extends PyModuleObject {
  @PyClassAttribute public PyObject PI;

  @PyClassAttribute public PyObject e;
  @PyClassAttribute public PyObject pi;

  @PyClassAttribute public PyObject inf;

  @PyClassAttribute private final PyObject nan;

  public math(PyUnicodeObject name) {
    super(name);
    PI = new PyFloatObject(Math.PI);
    pi = PI;
    e = new PyFloatObject(Math.E);
    inf = new PyFloatObject(Double.NEGATIVE_INFINITY);
    nan = new PyFloatObject(Double.NaN);
  }

  public static double[] getArraysFromTuple(PyTupleObject tuple) throws PyException {
    double[] res = new double[tuple.size()];
    for (int i = 0; i < tuple.size(); i++) {
      if (tuple.get(i) instanceof PyLongObject object) {
        res[i] = object.getData();
      }
      if (tuple.get(i) instanceof PyFloatObject object) {
        res[i] = object.getData();
      }
    }
    return res;
  }

  public static long factorial(int n) {
    long result = 1;
    for (int i = 1; i <= n; i++) {
      result *= i;
    }
    return result;
  }

  @PyClassMethod
  public PyObject fabs(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyNumberMethods num) {
        return num.abs();
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : fabs() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject ceil(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) return object;
      if (value instanceof PyFloatObject floatObject)
        return new PyFloatObject(Math.ceil(floatObject.getData()));
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : ceil() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject cos(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.cos(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.cos(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : cos() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject sin(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.sin(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.sin(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : sin() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject tan(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.tan(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.tan(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : tan() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject acos(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.acos(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.acos(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : acos() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject asin(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.asin(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.asin(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : asin() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject atan(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.atan(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.atan(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : atan() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject acosh(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(
            Math.log(object.getData() + Math.sqrt(object.getData() * object.getData() - 1)));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(
            Math.log(object.getData() + Math.sqrt(object.getData() * object.getData() - 1)));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : acosh() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject asinh(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(
            Math.log(object.getData() + Math.sqrt(object.getData() * object.getData() + 1)));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(
            Math.log(object.getData() + Math.sqrt(object.getData() * object.getData() + 1)));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : asinh() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject atanh(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(0.5 * Math.log((1 + object.getData()) / (1 - object.getData())));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(0.5 * Math.log((1 + object.getData()) / (1 - object.getData())));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : atanh() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject atan2(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var y = args.get(0);
      var x = args.get(1);
      if (y instanceof PyLongObject object1 && x instanceof PyLongObject object2) {
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
      if (y instanceof PyLongObject object1 && x instanceof PyFloatObject object2) {
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
      if (y instanceof PyFloatObject object1 && x instanceof PyLongObject object2) {
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
      if (y instanceof PyFloatObject object1 && x instanceof PyFloatObject object2) {
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : atan() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject copysign(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var x = args.get(0);
      var y = args.get(1);
      if (x instanceof PyLongObject object1 && y instanceof PyLongObject object2) {
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
      if (x instanceof PyLongObject object1 && y instanceof PyFloatObject object2) {
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyLongObject object2) {
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyFloatObject object2) {
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : copysign() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject degrees(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.toDegrees(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.toDegrees(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : degrees() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject dist(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var x = args.get(0);
      var y = args.get(1);
      if (x instanceof PyTupleObject object1 && y instanceof PyTupleObject object2) {
        double[] p1 = getArraysFromTuple(object1);
        double[] p2 = getArraysFromTuple(object2);
        if (p1.length != p2.length) {
          throw new PyException("the size of two argument not match");
        }
        double res = 0;
        for (int i = 0; i < p1.length; i++) {
          res += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        }
        return new PyFloatObject(Math.sqrt(res));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : dist() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject erf(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Erf.erf(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Erf.erf(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : erf() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject erfc(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Erf.erfc(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Erf.erfc(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : erfc() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject exp(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.exp(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.exp(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : exp() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject expm1(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyFloatObject(Math.exp(object.getData()) - 1);
      }
      if (value instanceof PyFloatObject object) {
        return new PyFloatObject(Math.exp(object.getData()) - 1);
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : expm1() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject floor(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyLongObject((long) Math.floor(object.getData()));
      }
      if (value instanceof PyFloatObject object) {
        return new PyLongObject((long) Math.floor(object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : floor() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject fmod(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var x = args.get(0);
      var y = args.get(1);
      if (x instanceof PyLongObject object1 && y instanceof PyLongObject object2) {
        return new PyFloatObject(
            object1.getData()
                - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
      if (x instanceof PyLongObject object1 && y instanceof PyFloatObject object2) {
        return new PyFloatObject(
            object1.getData()
                - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyLongObject object2) {
        return new PyFloatObject(
            object1.getData()
                - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyFloatObject object2) {
        return new PyFloatObject(
            object1.getData()
                - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : fmod() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject comb(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var value1 = args.get(0);
      var value2 = args.get(1);
      if (value1 instanceof PyLongObject object1 && value2 instanceof PyLongObject object2) {
        return new PyLongObject(
            factorial((int) object1.getData())
                / (factorial((int) object2.getData())
                    * factorial((int) (object1.getData() - object2.getData()))));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : comb() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject factorial(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) {
        return new PyLongObject(factorial((int) object.getData()));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : factorial() argument must be a number");
    return null;
  }

  @PyClassMethod
  public PyObject perm(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var value1 = args.get(0);
      var value2 = args.get(1);
      if (value1 instanceof PyLongObject object1) {
        if (value2 instanceof PyLongObject object2) {
          return new PyLongObject(
              factorial((int) object1.getData())
                  / factorial((int) (object1.getData() - object2.getData())));
        } else if (value2 instanceof PyNoneObject) {
          return new PyLongObject(factorial((int) object1.getData()));
        }
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "TypeError : perm() argument must be a number");
    return null;
  }
}
