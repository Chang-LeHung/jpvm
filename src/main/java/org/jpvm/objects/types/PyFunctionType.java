package org.jpvm.objects.types;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFunctionObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.pycParser.PyCodeObject;

public class PyFunctionType extends PyTypeType {
  public PyFunctionType() {
    super();
    name = "function";
  }

  public PyFunctionObject createFunction(PyCodeObject codeObject, PyDictObject globals,
                                         PyUnicodeObject name) {
    PyFunctionObject func = new PyFunctionObject(codeObject, globals, name);
    if (PyFunctionObject.__name__ == null) {
      PyFunctionObject.__name__ = new PyUnicodeObject("__main__");
      func.setFuncName(PyFunctionObject.__name__);
    } else {
      func.setFuncModule(globals.get(PyUnicodeObject.getOrCreateFromInternStringPool("__main__", true)));
    }
    return func;
  }
}
