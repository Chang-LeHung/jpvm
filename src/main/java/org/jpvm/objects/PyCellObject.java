package org.jpvm.objects;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.types.PyCellType;
import org.jpvm.objects.types.PyTypeType;

public class PyCellObject extends PyObject {

  public static final PyTypeType type = PyCellType.getInstance();

  private PyObject ref;

  public PyCellObject(PyObject ref) {
    this.ref = ref;
  }

  public PyCellObject() {}

  public PyObject getRef() {
    return ref;
  }

  public synchronized void setRef(PyObject ref) {
    this.ref = ref;
  }

  @Override
  public String toString() {
    return "cell<" + ref + ">";
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (o instanceof PyCellObject other) {
      return switch (op) {
        case Py_EQ -> ref.richCompare(other.ref, Operator.Py_EQ);
        case Py_NE -> ref.richCompare(other.ref, Operator.Py_NE);
        default -> throw new PyException("Invalid operator: " + op);
      };
    } else {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
          "Invalid operand type: " + o.getTypeName());
      return null;
    }
  }
}
