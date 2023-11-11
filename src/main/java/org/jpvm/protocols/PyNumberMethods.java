package org.jpvm.protocols;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.PyObject;

public interface PyNumberMethods {

  /** implementation of corresponding cpython nb_add */
  default PyObject add(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "add is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_subtract */
  default PyObject sub(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sub is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_multiply */
  default PyObject mul(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "mul is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_remainder */
  default PyObject mod(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "mod is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_divmod */
  default PyObject divmod(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "divmod is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_power */
  default PyObject pow(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "pow is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_negative */
  default PyObject neg() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "neg is not implemented");
    return null;
  }

  /** implementation of corresponding cpython tp_positive */
  default PyObject pos() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "pos is not implemented");
    return null;
  }

  /** implementation of corresponding cpython tp_absolute */
  default PyObject abs() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "abs is not implemented");
    return null;
  }

  /** implementation of corresponding cpython tp_bool */
  default PyObject bool() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "bool is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_invert */
  default PyObject invert() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "invert is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_lshift */
  default PyObject lshift(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "lshift is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_rshift */
  default PyObject rshift(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "rshift is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_and */
  default PyObject and(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "v is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_xor */
  default PyObject xor(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "xor is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_or */
  default PyObject or(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "or is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_int */
  default PyObject nbInt() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "nbInt is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_reserved */
  default PyObject reserved(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "reserved is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_float */
  default PyObject nbFloat() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "nbFloat is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_add */
  default PyObject inplaceAdd(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplaceAdd is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_subtract */
  default PyObject inplaceSub(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplaceSub is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_multiply */
  default PyObject inplaceMul(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplaceMul is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_remainder */
  default PyObject inplaceMod(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplaceMod is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_power */
  default PyObject inplacePow(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplacePow is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_lshift */
  default PyObject inplaceLshift(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "inplaceLshift is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_rshift */
  default PyObject inplaceRshift(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "inplaceRshift is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_and */
  default PyObject inplaceAnd(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "v is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_xor */
  default PyObject inplaceXor(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplaceXor is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_or */
  default PyObject inplaceOr(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "inplaceOr is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_floor_divide */
  default PyObject floorDiv(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "floorDiv is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_true_divide */
  default PyObject trueDiv(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "trueDiv is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_floor_divide */
  default PyObject inplaceFloorDiv(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "inplaceFloorDiv is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_true_divide */
  default PyObject inplaceTrueDiv(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "inplaceTrueDiv is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_index */
  default PyObject index() throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "index is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_matrix_multiply */
  default PyObject matrixMul(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "matrixMul is not implemented");
    return null;
  }

  /** implementation of corresponding cpython nb_inplace_matrix_multiply */
  default PyObject inplaceMatrixMul(PyObject o) throws PyException {
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.NotImplementedError, "inplaceMatrixMul is not implemented");
    return null;
  }
}
