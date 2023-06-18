package org.jpvm.objects.types;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;
import org.jpvm.pvm.Abstract;

/**
 * used in python code to represent a python type
 */
public class PyPythonType extends PyTypeType {
  public static PyObject type = PyTypeType.type;

  public PyPythonType() {
    super();
  }

  public PyPythonType(PyUnicodeObject name, PyTupleObject bases, PyDictObject dict) {
    super();
    this.name = dict.get(PyUnicodeObject.getOrCreateFromInternStringPool("__module__", true)) + "." + name.getData();
    this._bases = bases;
    this.dict = dict;
  }

  @Override
  public String toString() {
    return "PyPythonType:" + name;
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyPythonObject res = new PyPythonObject();
    PyUnicodeObject __init__ = PyUnicodeObject.getOrCreateFromInternStringPool("__init__", true);
    PyUnicodeObject __new__ = PyUnicodeObject.getOrCreateFromInternStringPool("__new__", true);
    callPythonCode(__new__, res, args, kwArgs);
    callPythonCode(__init__, res, args, kwArgs);
    res.setType(this);
    return res;
  }

  public PyObject callPythonCode(PyUnicodeObject name, PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyObject function = dict.get(name);
    args = Utils.packSelfAsTuple(self, args);
    if (function != null) {
      return Abstract.abstractCall(function, null, args, kwArgs);
    }
    return null;
  }
}
