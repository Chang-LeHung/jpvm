package org.jpvm.objects.pyinterface;

import org.jpvm.errors.PyMissMethod;
import org.jpvm.objects.PyMethodObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public interface TypeGetMethod {

  /**
   * @param name name of method will be loaded
   * @return {@link PyMethodObject} if not contain methodName then throw PyMissMethod
   */
  default PyObject getMethod(String name) throws PyMissMethod {
    throw new PyMissMethod("not have method" + name);
  }

  /**
   * @param name name of method will be loaded
   * @return {@link PyMethodObject} if not contain methodName then throw PyMissMethod
   */
  default PyObject getMethod(PyUnicodeObject name) throws PyMissMethod {
    throw new PyMissMethod("not have method" + name.toJavaType());
  }
}
