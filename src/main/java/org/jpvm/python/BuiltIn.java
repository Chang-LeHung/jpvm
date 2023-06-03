package org.jpvm.python;

import org.jpvm.objects.*;
import org.jpvm.objects.types.PyTypeType;


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

  public static PyDictObject dict;

  static {
    dict = new PyDictObject();
    dict.put(new PyUnicodeObject("int"), PyLongObject.type);
    dict.put(new PyUnicodeObject("list"), PyListObject.type);
    dict.put(new PyUnicodeObject("tuple"), PyTupleObject.type);
    dict.put(new PyUnicodeObject("dict"), PyDictObject.type);
    dict.put(new PyUnicodeObject("complex"), PyComplexObject.type);
    dict.put(new PyUnicodeObject("str"), PyUnicodeObject.type);
    dict.put(new PyUnicodeObject("type"), PyTypeType.type);
  }
}
