package org.jpvm.objects.types;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyListObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;

public class PyListType extends PyTypeType {
  public PyListType() {
    super();
    name = "list";
  }


  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyListObject result = new PyListObject();
      if (args.get(0) instanceof TypeDoIterate itr) {
        while (itr.hasNext()) {
          result.append(itr.next());
        }
      }else if (args.get(0) instanceof TypeIterable itr) {
        TypeDoIterate iterator = itr.getIterator();
        while (iterator.hasNext()) {
          result.append(iterator.next());
        }
      }else
        throw new PyTypeNotMatch("list() require an Iterable object");
      return result;
    }
    throw new PyException("list() only require one argument");
  }
}
