package org.jpvm.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class MRO {

  public static List<PyObject> mro(PyObject type) throws PyException {
    if (type instanceof PyTypeType t) {
      List<PyObject> res = new ArrayList<>();
      res.add(type);
      PyTupleObject bases = t.getBasesClass();
      List<List<PyObject>> linBases = new ArrayList<>();
      for (int i = 0; i < bases.size(); i++) {
        PyObject base = bases.get(i);
        if (base instanceof PyTypeType) {
          List<PyObject> mro = mro(base);
          linBases.add(mro);
        }
      }
      res.addAll(Objects.requireNonNull(merge(linBases)));
      return res;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "only a class can call mro");
    return null;
  }

  public static List<PyObject> merge(List<List<PyObject>> bases) throws PyException {
    var res = new ArrayList<PyObject>();
    while (true) {
      PyObject head = null;
      var types =
          bases.stream()
              .filter(x -> (!x.isEmpty()))
              .collect(Collectors.toCollection(ArrayList::new));
      if (types.isEmpty()) return res;
      boolean flag = false;
      for (List<PyObject> ts : types) {
        head = ts.get(0);
        PyObject h = head;
        if (types.stream().map(x -> x.subList(1, x.size())).allMatch(x -> (!x.contains(h)))) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        PyErrorUtils.pyErrorFormat(
            PyErrorUtils.RuntimeError, "MRO error: can not find a correct method resolution order");
        return null;
      }
      res.add(head);
      PyObject finalHead = head;
      types.forEach(
          x -> {
            if (x.get(0) == finalHead) x.remove(0);
          });
    }
  }
}
