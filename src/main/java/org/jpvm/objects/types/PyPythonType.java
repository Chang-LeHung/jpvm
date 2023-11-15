package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.vm.Abstract;

/** used in python code to represent a python type */
public class PyPythonType extends PyTypeType {
  public static final PyTypeType type = PyTypeType.type;

  public PyPythonType() {
    super(PyPythonObject.class);
  }

  public PyPythonType(PyUnicodeObject name, PyTupleObject bases, PyDictObject dict) {
    this();
    this.name =
        dict.get(PyUnicodeObject.getOrCreateFromInternStringPool("__module__", true))
            + "."
            + name.getData();
    this.__bases__ = bases;
    this.dict = dict;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public String toString() {
    return "PyPythonType:" + name;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyPythonObject res = new PyPythonObject();
    res.setType(this);
    PyUnicodeObject __init__ = PyUnicodeObject.getOrCreateFromInternStringPool("__init__", true);
    PyUnicodeObject __new__ = PyUnicodeObject.getOrCreateFromInternStringPool("__new__", true);
    callPythonCode(__new__, res, args, kwArgs);
    callPythonCode(__init__, res, args, kwArgs);
    return res;
  }

  public PyObject callPythonCode(
      PyUnicodeObject name, PyObject self, PyTupleObject args, PyDictObject kwArgs)
      throws PyException {
    PyObject function = getAttr(name);
    args = Utils.packSelfAsTuple(self, args);
    if (function != null) {
      return Abstract.abstractCall(function, null, args, kwArgs);
    }
    return null;
  }
}
