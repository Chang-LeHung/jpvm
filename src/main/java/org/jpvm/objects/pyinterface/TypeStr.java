package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.annotation.PyClassMethod;

public interface TypeStr {

  PyUnicodeObject str();

  @PyClassMethod
  default PyUnicodeObject __str__(PyTupleObject args, PyDictObject kwArgs) {
    return str();
  }
}
