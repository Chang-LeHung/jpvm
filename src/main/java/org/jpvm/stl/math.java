package org.jpvm.stl;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.protocols.PyNumberMethods;
import org.apache.commons.math3.special.Erf;


public class math extends PyModuleObject {
  @PyClassAttribute
  public PyObject PI;

  @PyClassAttribute
  public PyObject e;
  @PyClassAttribute
  public PyObject pi;

  @PyClassAttribute
  public PyObject inf;

  @PyClassAttribute
  private PyObject nan;

  public math(PyUnicodeObject name) {
    super(name);
    PI = new PyFloatObject(Math.PI);
    pi = PI;
    e = new PyFloatObject(Math.E);
    inf = new PyFloatObject(Double.NEGATIVE_INFINITY);
    nan = new PyFloatObject(Double.NaN);
  }


  @PyClassMethod
  public PyObject fabs(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyNumberMethods num) {
        return num.abs();
      }
    }
    throw new PyException("TypeError : fabs() argument must be a number");
  }

  @PyClassMethod
  public PyObject ceil(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object) return object;
      if (value instanceof PyFloatObject floatObject)
        return new PyFloatObject(Math.ceil(floatObject.getData()));
    }
    throw new PyException("TypeError : ceil() argument must be a number");
  }

  @PyClassMethod
  public PyObject cos(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.cos(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.cos(object.getData()));
      }
    }
    throw new PyException("TypeError : cos() argument must be a number");
  }

  @PyClassMethod
  public PyObject sin(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.sin(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.sin(object.getData()));
      }
    }
    throw new PyException("TypeError : sin() argument must be a number");
  }
  @PyClassMethod
  public PyObject tan(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.tan(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.tan(object.getData()));
      }
    }
    throw new PyException("TypeError : tan() argument must be a number");
  }
  @PyClassMethod
  public PyObject acos(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.acos(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.acos(object.getData()));
      }
    }
    throw new PyException("TypeError : acos() argument must be a number");
  }

  @PyClassMethod
  public PyObject asin(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.asin(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.asin(object.getData()));
      }
    }
    throw new PyException("TypeError : asin() argument must be a number");
  }

  @PyClassMethod
  public PyObject atan(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.atan(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.atan(object.getData()));
      }
    }
    throw new PyException("TypeError : atan() argument must be a number");

  }

  @PyClassMethod
  public PyObject acosh(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.log(object.getData() + Math.sqrt(object.getData()*object.getData() - 1)));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.log(object.getData() + Math.sqrt(object.getData()*object.getData() - 1)));
      }
    }
    throw new PyException("TypeError : acosh() argument must be a number");
  }

  @PyClassMethod
  public PyObject asinh(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.log(object.getData() + Math.sqrt(object.getData()*object.getData() + 1)));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.log(object.getData() + Math.sqrt(object.getData()*object.getData() + 1)));
      }
    }
    throw new PyException("TypeError : asinh() argument must be a number");
  }

  @PyClassMethod
  public PyObject atanh(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(0.5 * Math.log((1 + object.getData()) / (1 - object.getData())));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(0.5 * Math.log((1 + object.getData()) / (1 - object.getData())));
      }
    }
    throw new PyException("TypeError : atanh() argument must be a number");
  }

  @PyClassMethod
  public PyObject atan2(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var y = args.get(0);
      var x = args.get(1);
      if (y instanceof PyLongObject object1 && x instanceof PyLongObject object2){
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
      if (y instanceof PyLongObject object1 && x instanceof PyFloatObject object2){
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
      if (y instanceof PyFloatObject object1 && x instanceof PyLongObject object2){
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
      if (y instanceof PyFloatObject object1 && x instanceof PyFloatObject object2){
        return new PyFloatObject(Math.atan2(object1.getData(), object2.getData()));
      }
    }
    throw new PyException("TypeError : atan() argument must be a number");
  }

  @PyClassMethod
  public PyObject copysign(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var x = args.get(0);
      var y = args.get(1);
      if (x instanceof PyLongObject object1 && y instanceof PyLongObject object2){
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
      if (x instanceof PyLongObject object1 && y instanceof PyFloatObject object2){
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyLongObject object2){
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyFloatObject object2){
        return new PyFloatObject(Math.copySign(object1.getData(), object2.getData()));
      }
    }
    throw new PyException("TypeError : copysign() argument must be a number");
  }

  @PyClassMethod
  public PyObject degrees(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.toDegrees(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.toDegrees(object.getData()));
      }
    }
    throw new PyException("TypeError : degrees() argument must be a number");
  }

  @PyClassMethod
  public PyObject dist(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var x = args.get(0);
      var y = args.get(1);
      if(x instanceof PyTupleObject object1 && y instanceof PyTupleObject object2){
        double[] p1 = getArraysFromTuple(object1);
        double[] p2 = getArraysFromTuple(object2);
        if(p1.length != p2.length){
          throw new PyException("the size of two argument not match");
        }
        double res = 0;
        for(int i = 0; i < p1.length; i++){
          res += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        }
        return new PyFloatObject(Math.sqrt(res));
      }
    }
    throw new PyException("TypeError : dist() argument must be a number");
  }

  @PyClassMethod
  public PyObject erf(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Erf.erf(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Erf.erf(object.getData()));
      }
    }
    throw new PyException("TypeError : erf() argument must be a number");
  }

  @PyClassMethod
  public PyObject erfc(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Erf.erfc(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Erf.erfc(object.getData()));
      }
    }
    throw new PyException("TypeError : erfc() argument must be a number");
  }

  @PyClassMethod
  public PyObject exp(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.exp(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.exp(object.getData()));
      }
    }
    throw new PyException("TypeError : exp() argument must be a number");
  }

  @PyClassMethod
  public PyObject expm1(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyFloatObject(Math.exp(object.getData()) - 1);
      }
      if (value instanceof PyFloatObject object){
        return new PyFloatObject(Math.exp(object.getData()) - 1);
      }
    }
    throw new PyException("TypeError : expm1() argument must be a number");
  }

  @PyClassMethod
  public PyObject floor(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyLongObject((long) Math.floor(object.getData()));
      }
      if (value instanceof PyFloatObject object){
        return new PyLongObject((long) Math.floor(object.getData()));
      }
    }
    throw new PyException("TypeError : floor() argument must be a number");
  }

  @PyClassMethod
  public PyObject fmod(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var x = args.get(0);
      var y = args.get(1);
      if (x instanceof PyLongObject object1 && y instanceof PyLongObject object2){
        return new PyFloatObject(object1.getData() - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
      if (x instanceof PyLongObject object1 && y instanceof PyFloatObject object2){
        return new PyFloatObject(object1.getData() - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyLongObject object2){
        return new PyFloatObject(object1.getData() - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
      if (x instanceof PyFloatObject object1 && y instanceof PyFloatObject object2){
        return new PyFloatObject(object1.getData() - object2.getData() * Math.floor(object1.getData() / object2.getData()));
      }
    }
    throw new PyException("TypeError : fmod() argument must be a number");
  }

  @PyClassMethod
  public PyObject comb(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var value1 = args.get(0);
      var value2 = args.get(1);
      if (value1 instanceof PyLongObject object1 && value2 instanceof PyLongObject object2){
        return new PyLongObject(factorial((int) object1.getData()) / (factorial((int) object2.getData()) * factorial((int) (object1.getData()- object2.getData()))));
      }

    }
    throw new PyException("TypeError : comb() argument must be a number");
  }

  @PyClassMethod
  public PyObject factorial(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyLongObject object){
        return new PyLongObject(factorial((int) object.getData()));
      }
    }
    throw new PyException("TypeError : factorial() argument must be a number");
  }

  @PyClassMethod
  public PyObject perm(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      var value1 = args.get(0);
      var value2 = args.get(1);
      if (value1 instanceof PyLongObject object1){
        if(value2 instanceof PyLongObject object2){
          return new PyLongObject(factorial((int) object1.getData()) / factorial((int) (object1.getData() - object2.getData())));
        }else if(value2 instanceof PyNoneObject){
          return new PyLongObject(factorial((int) object1.getData()));
        }
      }
    }
    throw new PyException("TypeError : perm() argument must be a number");
  }

  public static double[] getArraysFromTuple(PyTupleObject tuple){
    double[] res = new double[tuple.size()];
    for(int i = 0; i < tuple.size(); i++){
      if(tuple.get(i) instanceof PyLongObject object){
        res[i] = object.getData();
      }
      if(tuple.get(i) instanceof PyFloatObject object){
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

}
