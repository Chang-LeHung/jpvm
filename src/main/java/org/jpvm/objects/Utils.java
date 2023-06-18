package org.jpvm.objects;

public class Utils {

  public static PyTupleObject packSelfAsTuple(PyObject self, PyTupleObject args) {
    PyTupleObject res = new PyTupleObject(args.size() + 1);
    res.set(0, self);

    for (int i = 0; i < args.size(); i++) {
      res.set(i + 1, args.get(i));
    }
    return res;
  }
}
