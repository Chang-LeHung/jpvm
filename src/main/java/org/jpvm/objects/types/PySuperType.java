package org.jpvm.objects.types;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;
import org.jpvm.pvm.JPVM;

public class PySuperType extends PyTypeType {
  public PySuperType(Class<?> clazz) {
    super(clazz);
  }

  public PySuperType() {
    this(null);
    name = "class_super";
  }

  @Override
  public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      PyFrameObject currentFrame = JPVM.getThreadState().getCurrentFrame();
      self = currentFrame.getLocal(0);
      PyTypeType tp = (PyTypeType) self.getType();
      return new PySuperObject(self, tp.getMro().get(1));
      //      return tp.getMro().get(1);
    } else if (args.size() == 2) {
      self = args.get(1);
      PyTypeType start = (PyTypeType) args.get(0);
      PyTypeType tp = (PyTypeType) self.getType();
      PyTupleObject tpMro = tp.getMro();
      for (int i = 0; i < tpMro.size(); i++) {
        if (tpMro.get(i) == start && i + 1 < tpMro.size()) {
          return new PySuperObject(self, tpMro.get(i + 1));
          //          return tpMro.get(i + 1);
        }
      }
      throw new PyException("can not find a proper super class");
    }
    throw new PyException("super() takes at most 2 arguments");
  }
}
