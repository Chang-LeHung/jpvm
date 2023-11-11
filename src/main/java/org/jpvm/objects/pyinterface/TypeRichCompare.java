package org.jpvm.objects.pyinterface;

import org.jpvm.excptions.objs.PyException;
import org.jpvm.objects.PyBoolObject;
import org.jpvm.objects.PyObject;

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
