package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.PyBoolObject;
import org.jpvm.objects.PyObject;

public interface TypeRichCompare {

  PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator;

  public static int Py_LT = 0;
  public static int Py_LE = 1;
  public static int Py_EQ = 2;
  public static int Py_NE = 3;
  public static int Py_GT = 4;
  public static int Py_GE = 5;
  public static int PyCmp_IN = 6;
  public static int PyCmp_NOT_IN = 7;
  public static int PyCmp_IS = 8;
  public static int PyCmp_IS_NOT = 9;
  public static int PyCmp_EXC_MATCH = 10;
  public static int PyCmp_BAD = 11;
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
