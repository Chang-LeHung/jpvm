package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.PyBoolObject;
import org.jpvm.objects.PyObject;

public interface TypeRichCompare {

  PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator;

  public static enum Operator {
    PY_EQ,
    PY_NE,
    PY_GT,
    PY_GE,
    PY_LE,
    PY_LT,
  }
}
