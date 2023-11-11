package org.jpvm.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jpvm.excptions.objs.PyException;
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
      res.addAll(merge(linBases));
      return res;
    }
    throw new PyException("only a class can call mro");
  }

  public static List<PyObject> merge(List<List<PyObject>> bases) {
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
      if (!flag)
        throw new RuntimeException("MRO error: can not find a correct method resolution order");
      res.add(head);
      PyObject finalHead = head;
      types.forEach(
          x -> {
            if (x.get(0) == finalHead) x.remove(0);
          });
    }
  }
}
