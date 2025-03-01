package org.jpvm.stl.atomic;

import org.jpvm.objects.PyModuleObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.annotation.PyClassAttribute;

public class PyModuleMain extends PyModuleObject {

  @PyClassAttribute private final PyVolatileType Volatile;

  @PyClassAttribute private final PyAtomicLongType AtomicLong;

  @PyClassAttribute private final PyAtomicLongType AtomicReference;

  public PyModuleMain(PyUnicodeObject moduleName) {
    super(moduleName);
    Volatile = PyVolatileType.getInstance();
    AtomicLong = PyAtomicLongType.getInstance();
    AtomicReference = PyAtomicLongType.getInstance();
  }
}
