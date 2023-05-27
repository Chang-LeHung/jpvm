package org.jpvm.internal;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyObject;

public class Global {

  public static PyObject notImplemented = new PyNotImplemented("method not implemented");

  public static PyObject typeNotMatch = new PyTypeNotMatch("type not match");
}
