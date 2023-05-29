package org.jpvm.python;

import org.jpvm.objects.*;

import java.io.PrintWriter;

public class BuiltIn {

  public static PyBoolObject True = new PyBoolObject(true);
  public static PyBoolObject False = new PyBoolObject(false);
  public static PyNoneObject None = new PyNoneObject();
  public static PyNoneObject NULL = new PyNoneObject();
  public static PyObject ELLIPSIS = new PyObject();

  public static PySetObject FROZENSET = new PySetObject(true);

  /**
   * define jpython internal exceptions
   */
  public static PyObject PyExcStopIteration = new PyExcStopIteration();


  public PyObject print(PyObject... objs) {
    PrintWriter writer = new PrintWriter(System.out);
    for (PyObject obj : objs) {

    }
    return BuiltIn.None;
  }
}
