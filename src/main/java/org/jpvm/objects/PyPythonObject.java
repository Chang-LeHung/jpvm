package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.pvm.Abstract;

/** used in python code to represent a python object */
public class PyPythonObject extends PyObject implements PyNumberMethods {

  private PyTypeType type;

  public PyPythonObject() {
    dict = new PyDictObject();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public PyObject getType() {
    return type;
  }

  public void setType(PyTypeType type) {
    this.type = type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return super.str();
  }

  @Override
  public PyUnicodeObject repr() {
    return super.repr();
  }

  @Override
  public PyLongObject hash() {
    return super.hash();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    return super.richCompare(o, op);
  }

  @Override
  public PyDictObject getDict() {
    return super.getDict();
  }

  @Override
  public PyObject add(PyObject o) throws PyException {
    // get attribute from class
    PyObject attr = getAttr(PyUnicodeObject.getOrCreateFromInternStringPool("__add__", true));
    if (attr instanceof PyMethodObject func) {
      PyTupleObject args = new PyTupleObject(1);
      args.set(0, o);
      return Abstract.abstractCall(func, this, args, null);
    }
    return PyNumberMethods.super.add(o);
  }

  @Override
  public PyObject sub(PyObject o) throws PyException {
    PyObject sub__ = getAttr(PyUnicodeObject.getOrCreateFromInternStringPool("__sub__", true));
    if(sub__ instanceof PyMethodObject func){
      PyTupleObject args = new PyTupleObject(1);
      args.set(0, o);
      return Abstract.abstractCall(func, this, args, null);
    }
    return PyNumberMethods.super.sub(o);
  }

  @Override
  public PyObject mul(PyObject o) throws PyException {
    PyObject mul__ = getAttr(PyUnicodeObject.getOrCreateFromInternStringPool("__mul__", true));
    if(mul__ instanceof PyMethodObject func){
      PyTupleObject args = new PyTupleObject(1);
      args.set(0, o);
      return Abstract.abstractCall(func, this, args, null);
    }
    return PyNumberMethods.super.mul(o);
  }

  @Override
  public PyObject mod(PyObject o) throws PyException {
    PyObject mod__ = getAttr(PyUnicodeObject.getOrCreateFromInternStringPool("__mod__", true));
    if(mod__ instanceof PyMethodObject func){
      PyTupleObject args = new PyTupleObject(1);
      args.set(0, o);
      return Abstract.abstractCall(func, this, args, null);
    }
    return PyNumberMethods.super.mod(o);
  }

}
