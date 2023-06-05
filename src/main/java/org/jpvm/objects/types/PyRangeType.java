package org.jpvm.objects.types;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;

public class PyRangeType extends PyTypeType {

  public PyRangeType() {
    super();
    name = "range";
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (!(object instanceof PyLongObject o))
        throw new PyException("PyRangeType require int as 1st parameter");
      return new PyRangeObject(0, (int) o.getData(), 1);
    } else if (args.size() == 2) {
      PyObject object = args.get(0);
      if (!(object instanceof PyLongObject o1))
        throw new PyException("PyRangeType require int as 1st parameter");
      object = args.get(1);
      if (!(object instanceof PyLongObject o2))
        throw new PyException("PyRangeType require int as 2nd parameter");
      if (o1.getData() > o2.getData())
        throw new PyException("PyRangeType require start less than end");
      return new PyRangeObject((int) o1.getData(), (int) o2.getData(), 1);
    } else {
      PyObject object = args.get(0);
      if (!(object instanceof PyLongObject o1))
        throw new PyException("PyRangeType require int as 1st parameter");
      object = args.get(1);
      if (!(object instanceof PyLongObject o2))
        throw new PyException("PyRangeType require int as 2nd parameter");
      object = args.get(2);
      if (!(object instanceof PyLongObject o3))
        throw new PyException("PyRangeType require int as 3rd parameter");
      return new PyRangeObject((int) o1.getData(), (int) o2.getData(), (int) o3.getData());
    }
  }
}
