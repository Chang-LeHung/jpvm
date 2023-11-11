package org.jpvm.objects.types;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.protocols.PyNumberMethods;

public class PyFloatType extends PyTypeType {

  public PyFloatType() {
    super(PyFloatObject.class);
    name = "float";
    mro.add(PyLongObject.type);
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      return new PyFloatObject(0);
    } else if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyNumberMethods num) {
        return num.nbFloat();
      }
    }
    throw new PyException("TypeError float() takes at most 1 argument (" + args.size() + " given)");
  }
}
