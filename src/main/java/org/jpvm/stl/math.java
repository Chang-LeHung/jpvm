package org.jpvm.stl;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.protocols.PyNumberMethods;

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
  public PyObject abs(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      var value = args.get(0);
      if (value instanceof PyNumberMethods num) {
        return num.abs();
      }
    }
    throw new PyException("TypeError : abs() argument must be a number");
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
}
