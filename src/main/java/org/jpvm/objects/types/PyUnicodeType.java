package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyUnicodeType extends PyTypeType {
  private PyUnicodeType() {
    super(PyUnicodeObject.class);
    name = "str";
  }

  public static final class SelfHolder {
    public static final PyUnicodeType self = new PyUnicodeType();
  }

  public static PyUnicodeType getInstance() {
    return SelfHolder.self;
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
