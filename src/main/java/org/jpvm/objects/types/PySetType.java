package org.jpvm.objects.types;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;

public class PySetType extends PyTypeType {
  public PySetType() {
    super(PySetObject.class);
    name = "set";
  }

  /** code like `set()` or `set(listobject)` will call this method */
  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      return new PySetObject();
    } else {
      PyObject iterable = args.get(0);
      PySetObject result = new PySetObject();
      if (iterable instanceof TypeIterable itr) {
        TypeDoIterate iterator = itr.getIterator();
        while (iterator.hasNext()) {
          result.add(iterator.next());
        }
        return result;
      } else if (iterable instanceof TypeDoIterate iterator) {
        while (iterator.hasNext()) {
          result.add(iterator.next());
        }
        return result;
      }
    }
    throw new PyException("invalid argument in PySetType");
  }
}
