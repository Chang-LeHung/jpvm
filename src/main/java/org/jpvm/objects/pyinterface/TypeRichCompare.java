package org.jpvm.objects.pyinterface;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyBoolObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeRichCompare {

  int Py_LT = 0;
  int Py_LE = 1;
  int Py_EQ = 2;
  int Py_NE = 3;
  int Py_GT = 4;
  int Py_GE = 5;
  int PyCmp_IN = 6;
  int PyCmp_NOT_IN = 7;
  int PyCmp_IS = 8;
  int PyCmp_IS_NOT = 9;
  int PyCmp_EXC_MATCH = 10;
  int PyCmp_BAD = 11;

  PyBoolObject richCompare(PyObject o, Operator op) throws PyException;

  @PyClassMethod
  default PyObject __lt__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      richCompare(args.get(0), Operator.Py_LT);
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, String.format("%s __lt__ only require 1 argument", this));
  }

  @PyClassMethod
  default PyObject __le__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      richCompare(args.get(0), Operator.Py_LE);
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, String.format("%s __le__ only require 1 argument", this));
  }

  @PyClassMethod
  default PyObject __eq__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      richCompare(args.get(0), Operator.Py_EQ);
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, String.format("%s __eq__ only require 1 argument", this));
  }

  @PyClassMethod
  default PyObject __ne__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      richCompare(args.get(0), Operator.Py_NE);
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, String.format("%s __ne__ only require 1 argument", this));
  }

  @PyClassMethod
  default PyObject __gt__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      richCompare(args.get(0), Operator.Py_GT);
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, String.format("%s __gt__ only require 1 argument", this));
  }

  @PyClassMethod
  default PyObject __ge__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      richCompare(args.get(0), Operator.Py_GE);
    }
    return PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, String.format("%s __ge__ only require 1 argument", this));
  }

  enum Operator {
    Py_EQ,
    Py_NE,
    Py_GT,
    Py_GE,
    Py_LE,
    Py_LT,
    PyCmp_IN,
    PyCmp_NOT_IN,
    PyCmp_IS,
    PyCmp_IS_NOT,
    PyCmp_EXC_MATCH,
    PyCmp_BAD
  }
}
