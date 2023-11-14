package org.jpvm.objects;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.types.PyComplexType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.python.BuiltIn;

public class PyComplexObject extends PyObject implements PyNumberMethods {

  public static final PyObject type = new PyComplexType();
  private PyFloatObject real;
  private PyFloatObject image;

  public PyComplexObject() {}

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

  public synchronized void setReal(PyFloatObject real) {
    this.real = real;
  }

  public PyFloatObject getImage() {
    return image;
  }

  public synchronized void setImage(PyFloatObject image) {
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
    return new double[] {(double) getImage().toJavaType(), (double) getReal().toJavaType()};
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
    return new PyLongObject((int) (real.getData() * image.getData()));
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (Operator.Py_EQ != op) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
      return null;
    }
    if (o instanceof PyComplexObject complex) {
      if (complex.real == real && complex.image == image) return BuiltIn.True;
    }
    return BuiltIn.False;
  }

  @Override
  public PyBoolObject isHashable() {
    return BuiltIn.True;
  }

  @Override
  public PyObject add(PyObject o) throws PyException {
    if (!(o instanceof PyComplexObject)) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "can not apply function add on complex and " + o.getTypeName());
      return null;
    }
    return new PyComplexObject(
        (PyFloatObject) (real.add(((PyComplexObject) o).getReal())),
        (PyFloatObject) (image.add(((PyComplexObject) o).getImage())));
  }

  @Override
  public PyObject sub(PyObject o) throws PyException {
    if (!(o instanceof PyComplexObject)) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "cannot  apply function add on complex and " + o.getTypeName());
      return null;
    }
    return new PyComplexObject(
        (PyFloatObject) (real.sub(((PyComplexObject) o).getReal())),
        (PyFloatObject) (image.sub(((PyComplexObject) o).getImage())));
  }

  @Override
  public PyObject mul(PyObject o) throws PyException {
    if (!(o instanceof PyComplexObject)) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "can not apply function mul on complex and " + o.getTypeName());
      return null;
    }
    PyFloatObject ac = (PyFloatObject) real.mul(((PyComplexObject) o).getReal());
    PyFloatObject bd = (PyFloatObject) image.mul(((PyComplexObject) o).getImage());
    PyFloatObject bc = (PyFloatObject) image.mul(((PyComplexObject) o).getReal());
    PyFloatObject ad = (PyFloatObject) real.mul(((PyComplexObject) o).getImage());
    return new PyComplexObject((PyFloatObject) ac.sub(bd), (PyFloatObject) bc.add(ad));
  }

  @Override
  public PyObject pow(PyObject o) throws PyException {
    return PyNumberMethods.super.pow(o);
  }

  @Override
  public PyObject neg() throws PyException {
    return PyNumberMethods.super.neg();
  }

  @Override
  public PyObject pos() throws PyException {
    return PyNumberMethods.super.pos();
  }

  @Override
  public PyObject abs() throws PyException {
    return PyNumberMethods.super.abs();
  }

  @Override
  public PyObject bool() throws PyException {
    return PyNumberMethods.super.bool();
  }

  @Override
  public PyObject trueDiv(PyObject o) throws PyException {
    if (!(o instanceof PyComplexObject)) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "cannot apply function div on complex and " + o.getTypeName());
      return null;
    }
    PyFloatObject ac = (PyFloatObject) real.mul(((PyComplexObject) o).getReal());
    PyFloatObject bd = (PyFloatObject) image.mul(((PyComplexObject) o).getImage());
    PyFloatObject bc = (PyFloatObject) image.mul(((PyComplexObject) o).getReal());
    PyFloatObject ad = (PyFloatObject) real.mul(((PyComplexObject) o).getImage());
    PyFloatObject c2 = (PyFloatObject) image.pow(new PyLongObject(2));
    PyFloatObject d2 = (PyFloatObject) ((PyComplexObject) o).getImage().pow(new PyLongObject(2));
    PyFloatObject ac_add_bd = (PyFloatObject) ac.add(bd);
    PyFloatObject c2_add_d2 = (PyFloatObject) c2.add(d2);
    PyFloatObject bc_sub_ad = (PyFloatObject) bc.sub(ad);
    return new PyComplexObject(
        (PyFloatObject) ac_add_bd.trueDiv(c2_add_d2), (PyFloatObject) bc_sub_ad.trueDiv(c2_add_d2));
  }

  @Override
  public PyObject inplaceAdd(PyObject o) throws PyException {
    return add(o);
  }

  @Override
  public PyObject inplaceSub(PyObject o) throws PyException {
    return sub(o);
  }

  @Override
  public PyObject inplaceMul(PyObject o) throws PyException {
    return mul(o);
  }

  @Override
  public PyObject inplacePow(PyObject o) throws PyException {
    return pow(o);
  }

  @Override
  public PyObject inplaceTrueDiv(PyObject o) throws PyException {
    return trueDiv(o);
  }
}
