package org.jpvm.objects.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyTypeNotMatch;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyListObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;

public class PyListType extends PyTypeType {
  private PyListType() {
    super(PyListObject.class);
    name = "list";
  }

  public static final class SelfHolder {
    public static final PyListType self = new PyListType();
  }

  public static PyListType getInstance() {
    return SelfHolder.self;
  }

  public static PyListObject getListFromIterable(PyTupleObject args, PyDictObject kwArgs)
      throws PyException {
    if (args.size() == 0)
      return new PyListObject();
    if (args.size() == 1) {
      PyListObject result = new PyListObject();
      if (args.get(0) instanceof TypeDoIterate itr) {
        while (itr.hasNext()) {
          result.append(itr.next());
        }
      } else if (args.get(0) instanceof TypeIterable itr) {
        TypeDoIterate iterator = itr.getIterator();
        while (iterator.hasNext()) {
          result.append(iterator.next());
        }
      } else
        throw new PyTypeNotMatch("list() require an Iterable object");
      return result;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "list() only require one argument");
    return null;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return getListFromIterable(args, kwArgs);
  }
}
