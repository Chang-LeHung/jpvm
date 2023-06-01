package org.jpvm.objects;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.types.PyComplexType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.python.BuiltIn;

public class PyComplexObject extends PyObject implements PyNumberMethods {

  public static PyObject type = new PyComplexType();
  private PyFloatObject real;
  private PyFloatObject image;

  public PyComplexObject() {
  }

  public PyComplexObject(PyFloatObject real, PyFloatObject image) {
    this.real = real;
    this.image = image;
  }

  public static PyBoolObject check(PyObject o) {
    return new PyBoolObject(o == type);
  }

  public PyFloatObject getReal() {
    return real;
  }

  public void setReal(PyFloatObject real) {
    this.real = real;
  }

  public PyFloatObject getImage() {
    return image;
  }

  public void setImage(PyFloatObject image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return real + "+" + image + "i";
  }

  /**
   * @return double[] [0] for image [1] for real
   */
  @Override
  public Object toJavaType() {
    return new double[]{(double) getImage().toJavaType(),
        (double) getReal().toJavaType()};
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return super.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return repr();
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyLongObject hash() {
    return new PyLongObject((int)(real.getData()*image.getData()));
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (Operator.PY_EQ == op) {
      if (o instanceof PyComplexObject complex) {
        if (complex.real == real && complex.image == image)
          return BuiltIn.True;
      }
      return BuiltIn.False;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
  }

  @Override
  public PyBoolObject isHashable() {
    return BuiltIn.True;
  }

  @Override
  public PyObject add(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return PyNumberMethods.super.add(o);
  }

  @Override
  public PyObject sub(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return PyNumberMethods.super.sub(o);
  }

  @Override
  public PyObject mul(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return PyNumberMethods.super.mul(o);
  }

  @Override
  public PyObject pow(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return PyNumberMethods.super.pow(o);
  }

  @Override
  public PyObject neg() throws PyNotImplemented {
    return PyNumberMethods.super.neg();
  }

  @Override
  public PyObject pos() throws PyNotImplemented {
    return PyNumberMethods.super.pos();
  }

  @Override
  public PyObject abs() throws PyNotImplemented {
    return PyNumberMethods.super.abs();
  }

  @Override
  public PyObject bool() throws PyNotImplemented {
    return PyNumberMethods.super.bool();
  }

  @Override
  public PyObject trueDiv(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return PyNumberMethods.super.trueDiv(o);
  }
}
