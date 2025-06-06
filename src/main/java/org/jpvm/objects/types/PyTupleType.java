package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyTypeNotMatch;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;

public class PyTupleType extends PyTypeType {
  private PyTupleType() {
    super(PyTupleObject.class);
    name = "tuple";
  }

  public static final class SelfHolder {
    public static final PyTupleType self = new PyTupleType();
  }

  public static PyTupleType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0)
      return PyTupleObject.zero;
    if (args.size() == 1) {
      if (args.get(0) instanceof TypeDoIterate itr) {
        PyTupleObject result = new PyTupleObject(itr.size());
        while (itr.hasNext()) {
          result.add(itr.next());
        }
        return result;
      } else if (args.get(0) instanceof TypeIterable itr) {
        TypeDoIterate iterator = itr.getIterator();
        PyTupleObject result = new PyTupleObject(iterator.size());
        int index = 0;
        while (iterator.hasNext()) {
          result.set(index++, iterator.next());
        }
        return result;
      } else
        throw new PyTypeNotMatch("tuple() require an Iterable object");
    }
    throw new PyException("tuple() require 0 or 1 argument");
  }
}
