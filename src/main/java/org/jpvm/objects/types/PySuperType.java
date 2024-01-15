package org.jpvm.objects.types;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.vm.JPVM;

public class PySuperType extends PyTypeType {
  private PySuperType(Class<?> clazz) {
    super(clazz);
  }

  private PySuperType() {
    this(PySuperObject.class);
    name = "class_super";
  }

  public static final class SelfHolder {
    public static final PySuperType self = new PySuperType(PySuperObject.class);
  }

  public static PySuperType getInstance() {
    return SelfHolder.self;
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyObject self;
    if (args.size() == 0) {
      PyFrameObject currentFrame = JPVM.getThreadState().getCurrentFrame();
      self = currentFrame.getLocal(0);
      PyTypeType tp = (PyTypeType) self.getType();
      return new PySuperObject(self, tp.getMro().get(1));
    } else if (args.size() == 2) {
      self = args.get(1);
      PyTypeType start = (PyTypeType) args.get(0);
      PyTypeType tp = (PyTypeType) self.getType();
      PyListObject tpMro = tp.getMro();
      for (int i = 0; i < tpMro.size(); i++) {
        if (tpMro.get(i) == start && i + 1 < tpMro.size()) {
          return new PySuperObject(self, tpMro.get(i + 1));
        }
      }
      return PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "can not find a proper super class");
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "super() takes at most 2 arguments");
  }
}
