package org.jpvm.objects.types;

import org.jpvm.errors.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.protocols.PyNumberMethods;

public class PyLongType extends PyTypeType {

  public PyLongType() {
    super(PyLongObject.class);
    name = "int";
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      return PyLongObject.getLongObject(0);
    } else if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyNumberMethods num) {
        return num.nbInt();
      }
    }
    throw new PyException(
        "TypeError: int() takes exactly one argument (" + args.size() + " given)");
  }
}
