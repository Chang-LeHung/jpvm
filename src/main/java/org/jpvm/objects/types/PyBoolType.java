package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyBoolObject;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.protocols.PyNumberMethods;

public class PyBoolType extends PyTypeType {

  private PyBoolType() {
    super(PyBoolObject.class);
    name = "bool";
  }

  public static final class SelfHolder {
    public static final PyBoolType self = new PyBoolType();
  }

  public static PyBoolType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args != null && args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyNumberMethods num) {
        return num.bool();
      }
    }
    throw new PyException("bool require only 1 argument");
  }
}
