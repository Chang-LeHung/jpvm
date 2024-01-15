package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyMethodObject;
import org.jpvm.objects.PyUnicodeObject;

public interface TypeMethod {

  /**
   * @param methodName name of method will be executed
   * @return {@link PyMethodObject} if not contain methodName return {@link
   *     org.jpvm.internal.Global#notImplemented}
   */
  PyMethodObject getMethod(PyUnicodeObject methodName);
}
