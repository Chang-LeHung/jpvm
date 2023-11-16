package org.jpvm.objects.types;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.*;

public class PyRangeType extends PyTypeType {

  private PyRangeType() {
    super(PyRangeObject.class);
    name = "range";
  }

  public static final class SelfHolder {
    public static final PyRangeType self = new PyRangeType();
  }

  public static PyRangeType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (!(object instanceof PyLongObject o))
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require int as 1st parameter");
      return new PyRangeObject(0, (int) o.getData(), 1);
    } else if (args.size() == 2) {
      PyObject object = args.get(0);
      if (!(object instanceof PyLongObject o1))
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require int as 1st parameter");
      object = args.get(1);
      if (!(object instanceof PyLongObject o2))
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require int as 2nd parameter");
      if (o1.getData() > o2.getData())
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require start less than end");
      return new PyRangeObject((int) o1.getData(), (int) o2.getData(), 1);
    } else {
      PyObject object = args.get(0);
      if (!(object instanceof PyLongObject o1))
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require int as 1st parameter");
      object = args.get(1);
      if (!(object instanceof PyLongObject o2))
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require int as 2nd parameter");
      object = args.get(2);
      if (!(object instanceof PyLongObject o3))
        return PyErrorUtils.pyErrorFormat(
            PyErrorUtils.TypeError, "PyRangeType require int as 3rd parameter");
      return new PyRangeObject((int) o1.getData(), (int) o2.getData(), (int) o3.getData());
    }
  }
}
