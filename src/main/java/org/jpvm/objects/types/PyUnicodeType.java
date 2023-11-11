package org.jpvm.objects.types;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyUnicodeType extends PyTypeType {
  public PyUnicodeType() {
    super(PyUnicodeObject.class);
    name = "str";
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      return PyUnicodeObject.getOrCreateFromInternStringPool("", true);
    } else if (args.size() == 1) {
      return args.get(0).str();
    }
    throw new PyException("str() takes at most 1 argument (" + args.size() + " given)");
  }
}
