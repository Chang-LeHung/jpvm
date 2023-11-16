package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeRepr {

  /**
   * implementation of corresponding cpython tp_repr
   *
   * @return {@link PyUnicodeObject}
   */
  PyUnicodeObject repr();

  @PyClassMethod
  PyUnicodeObject __repr__(PyTupleObject args, PyDictObject kwArgs);

}
