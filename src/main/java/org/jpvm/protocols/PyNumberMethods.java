package org.jpvm.protocols;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyObject;

public interface PyNumberMethods {

  /**
   * implementation of corresponding cpython nb_add
   */
  default PyObject add(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("add is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_subtract
   */
  default PyObject sub(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("sub is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_multiply
   */
  default PyObject mul(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("mul is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_remainder
   */
  default PyObject mod(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("mod is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_divmod
   */
  default PyObject divmod(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("divmod is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_power
   */
  default PyObject pow(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("pow is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_negative
   */
  default PyObject neg() throws PyNotImplemented {
    throw new PyNotImplemented("neg is not implemented");
  }

  /**
   * implementation of corresponding cpython tp_positive
   */
  default PyObject pos() throws PyNotImplemented {
    throw new PyNotImplemented("pos is not implemented");
  }

  /**
   * implementation of corresponding cpython tp_absolute
   */
  default PyObject abs() throws PyNotImplemented {
    throw new PyNotImplemented("abs is not implemented");
  }

  /**
   * implementation of corresponding cpython tp_bool
   */
  default PyObject bool() throws PyNotImplemented {
    throw new PyNotImplemented("bool is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_invert
   */
  default PyObject invert() throws PyNotImplemented {
    throw new PyNotImplemented("invert is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_lshift
   */
  default PyObject lshift(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("lshift is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_rshift
   */
  default PyObject rshift(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("rshift is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_and
   */
  default PyObject and(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("v is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_xor
   */
  default PyObject xor(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("xor is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_or
   */
  default PyObject or(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("or is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_int
   */
  default PyObject nbInt() throws PyNotImplemented {
    throw new PyNotImplemented("nbInt is not implemented");
  }
  /**
   * implementation of corresponding cpython nb_reserved
   */
  default PyObject reserved(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("reserved is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_float
   */
  default PyObject nbFloat() throws PyNotImplemented {
    throw new PyNotImplemented("nbFloat is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_add
   */
  default PyObject inplaceAdd(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceAdd is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_subtract
   */
  default PyObject inplaceSub(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceSub is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_multiply
   */
  default PyObject inplaceMul(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceMul is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_remainder
   */
  default PyObject inplaceMod(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceMod is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_power
   */
  default PyObject inplacePow(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplacePow is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_lshift
   */
  default PyObject inplaceLshift(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceLshift is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_rshift
   */
  default PyObject inplaceRshift(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceRshift is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_and
   */
  default PyObject inplaceAnd(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("v is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_xor
   */
  default PyObject inplaceXor(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("inplaceXor is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_or
   */
  default PyObject inplaceOr(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("inplaceOr is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_floor_divide
   */
  default PyObject floorDiv(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("floorDiv is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_true_divide
   */
  default PyObject trueDiv(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    throw new PyNotImplemented("trueDiv is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_floor_divide
   */
  default PyObject inplaceFloorDiv(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceFloorDiv is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_true_divide
   */
  default PyObject inplaceTrueDiv(PyObject o) throws PyNotImplemented {
    throw new PyNotImplemented("inplaceTrueDiv is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_index
   */
  default PyObject index() throws PyNotImplemented {
    throw new PyNotImplemented("index is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_matrix_multiply
   */
  default PyObject matrixMul() throws PyNotImplemented {
    throw new PyNotImplemented("matrixMul is not implemented");
  }

  /**
   * implementation of corresponding cpython nb_inplace_matrix_multiply
   */
  default PyObject inplaceMatrixMul() throws PyNotImplemented {
    throw new PyNotImplemented("inplaceMatrixMul is not implemented");
  }
}
