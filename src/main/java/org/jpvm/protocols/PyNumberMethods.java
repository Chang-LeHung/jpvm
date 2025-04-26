package org.jpvm.protocols;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface PyNumberMethods {

  /** implementation of corresponding cpython nb_add */
  default PyObject add(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "add is not implemented");
  }

  @PyClassMethod
  default PyObject __add__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return add(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__add__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __radd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return add(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__radd__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_subtract */
  default PyObject sub(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "sub is not implemented");
  }

  @PyClassMethod
  default PyObject __sub__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sub(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__sub__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rsub__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return sub(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rsub__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_multiply */
  default PyObject mul(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "mul is not implemented");
  }

  @PyClassMethod
  default PyObject __mul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return mul(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__mul__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rmul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return mul(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rmul__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_remainder */
  default PyObject mod(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "mod is not implemented");
  }

  @PyClassMethod
  default PyObject __mod__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return mod(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__mod__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rmod__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return mod(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rmod__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_divmod */
  default PyObject divmod(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "divmod is not implemented");
  }

  @PyClassMethod
  default PyObject __divmod__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return divmod(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__divmod__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rdivmod__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return divmod(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rdivmod__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_power */
  default PyObject pow(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "pow is not implemented");
  }

  @PyClassMethod
  default PyObject __pow__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return pow(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__pow__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rpow__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return pow(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rpow__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_negative */
  default PyObject neg() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "neg is not implemented");
  }

  @PyClassMethod
  default PyObject __neg__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return neg();
  }

  /** implementation of corresponding cpython tp_positive */
  default PyObject pos() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "pos is not implemented");
  }

  @PyClassMethod
  default PyObject __pos__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return pos();
  }

  /** implementation of corresponding cpython tp_absolute */
  default PyObject abs() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "abs is not implemented");
  }

  @PyClassMethod
  default PyObject __abs__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return abs();
  }

  /** implementation of corresponding cpython tp_bool */
  default PyObject bool() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "bool is not implemented");
  }

  @PyClassMethod
  default PyObject __bool__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return bool();
  }

  /** implementation of corresponding cpython nb_invert */
  default PyObject invert() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "invert is not implemented");
  }

  @PyClassMethod
  default PyObject __invert__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return invert();
  }

  /** implementation of corresponding cpython nb_lshift */
  default PyObject lshift(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "lshift is not implemented");
  }

  @PyClassMethod
  default PyObject __lshift__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return lshift(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__lshift__ takes exactly no argument");
  }

  @PyClassMethod
  default PyObject __rlshift__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return lshift(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rlshift__ takes exactly no argument");
  }

  /** implementation of corresponding cpython nb_rshift */
  default PyObject rshift(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "rshift is not implemented");
  }

  @PyClassMethod
  default PyObject __rshift__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return rshift(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__lshift__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rrshift__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return rshift(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rrshift__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_and */
  default PyObject and(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "v is not implemented");
  }

  @PyClassMethod
  default PyObject __and__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return and(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__and__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rand__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return and(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rand__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_xor */
  default PyObject xor(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "xor is not implemented");
  }

  @PyClassMethod
  default PyObject __xor__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return xor(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__xor__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rxor__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return xor(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rxor__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_or */
  default PyObject or(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "or is not implemented");
  }

  @PyClassMethod
  default PyObject __or__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return or(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__or__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __ror__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return or(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__ror__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_int */
  default PyObject nbInt() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "nbInt is not implemented");
  }

  @PyClassMethod
  default PyObject __int__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return nbInt();
  }

  /** implementation of corresponding cpython nb_reserved */
  default PyObject reserved(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "reserved is not implemented");
  }

  /** implementation of corresponding cpython nb_float */
  default PyObject nbFloat() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "nbFloat is not implemented");
  }

  @PyClassMethod
  default PyObject __float__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return nbFloat();
  }

  /** implementation of corresponding cpython nb_inplace_add */
  default PyObject inplaceAdd(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceAdd is not implemented");
  }

  @PyClassMethod
  default PyObject __iadd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceAdd(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__iadd__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_subtract */
  default PyObject inplaceSub(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceSub is not implemented");
  }

  @PyClassMethod
  default PyObject __isub__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceSub(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__isub__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_multiply */
  default PyObject inplaceMul(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceMul is not implemented");
  }

  @PyClassMethod
  default PyObject __imul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceMul(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__imul__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_remainder */
  default PyObject inplaceMod(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceMod is not implemented");
  }

  @PyClassMethod
  default PyObject __imod__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceMod(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__imod__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_power */
  default PyObject inplacePow(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplacePow is not implemented");
  }

  @PyClassMethod
  default PyObject __ipow__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplacePow(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__ipow__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_lshift */
  default PyObject inplaceLshift(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceLshift is not implemented");
  }

  @PyClassMethod
  default PyObject __ilshift__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceLshift(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__ilshift__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_rshift */
  default PyObject inplaceRshift(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceRshift is not implemented");
  }

  @PyClassMethod
  default PyObject __irshift__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceRshift(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__irshift__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_and */
  default PyObject inplaceAnd(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "v is not implemented");
  }

  @PyClassMethod
  default PyObject __iand__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceAnd(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__iand__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_xor */
  default PyObject inplaceXor(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceXor is not implemented");
  }

  @PyClassMethod
  default PyObject __ixor__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceXor(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__ixor__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_or */
  default PyObject inplaceOr(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceOr is not implemented");
  }

  @PyClassMethod
  default PyObject __ior__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceOr(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "__ior__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_floor_divide */
  default PyObject floorDiv(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "floorDiv is not implemented");
  }

  @PyClassMethod
  default PyObject __floordiv__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return floorDiv(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__floordiv__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_true_divide */
  default PyObject trueDiv(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "trueDiv is not implemented");
  }

  @PyClassMethod
  default PyObject __truediv__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return trueDiv(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__itruediv__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_floor_divide */
  default PyObject inplaceFloorDiv(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceFloorDiv is not implemented");
  }

  @PyClassMethod
  default PyObject __ifloordiv__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceFloorDiv(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__ifloordiv__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_true_divide */
  default PyObject inplaceTrueDiv(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceTrueDiv is not implemented");
  }

  @PyClassMethod
  default PyObject __itruediv__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return inplaceTrueDiv(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__itruediv__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_index */
  default PyObject index() throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError, "index is not implemented");
  }

  @PyClassMethod
  default PyObject __index__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return index();
  }

  /** implementation of corresponding cpython nb_matrix_multiply */
  default PyObject matrixMul(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "matrixMul is not implemented");
  }

  @PyClassMethod
  default PyObject __matmul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return matrixMul(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__matmul__ takes exactly one argument");
  }

  @PyClassMethod
  default PyObject __rmatmul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return matrixMul(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__rmatmul__ takes exactly one argument");
  }

  /** implementation of corresponding cpython nb_inplace_matrix_multiply */
  default PyObject inplaceMatrixMul(PyObject o) throws PyException {
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.NotImplementedError,
        "inplaceMatrixMul is not implemented");
  }

  @PyClassMethod
  default PyObject __imatmul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1)
      return matrixMul(args.get(0));
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "__imatmul__ takes exactly one argument");
  }
}
