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
  public PyObject pi;

  public math(PyUnicodeObject name) {
    super(name);
    PI = new PyFloatObject(Math.PI);
    pi = PI;
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
}