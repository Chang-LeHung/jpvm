package org.jpvm.python;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyTypeError;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.module.filestream.PyFileStreamObject;
import org.jpvm.module.sys.Sys;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyListType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.pvm.Abstract;

import java.io.IOException;


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
  public static PyObject notImplemented = new PyObject();
  public static PyDictObject dict;
  public static Class<?>[] parameterTypes = new Class<?>[]{PyTupleObject.class, PyDictObject.class};

  static {
    PyObject.registerBaseObjectType();
    dict = new PyDictObject();
    try {
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("int", true),
          PyLongObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("list", true),
          PyListObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("tuple", true),
          PyTupleObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("dict", true),
          PyDictObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("bool", true),
          PyBoolObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("complex", true),
          PyComplexObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("str", true),
          PyUnicodeObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("type", true),
          PyTypeType.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("True", true),
          BuiltIn.True);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("False", true),
          BuiltIn.False);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("None", true),
          BuiltIn.None);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("Ellipsis", true),
          BuiltIn.ELLIPSIS);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("object", true),
          PyObject.type);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("stdout", true),
          Sys.stdout);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("range", true),
          PyRangeObject.type);
    } catch (PyException e) {
      throw new RuntimeException(e);
    }
  }

  public static void doInit() {
    Class<BuiltIn> clazz = BuiltIn.class;
    try {
      // load builtin function & object into builtin dict
      PyNativeMethodObject mp = new PyNativeMethodObject(clazz.getMethod("print", parameterTypes), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("print", true), mp);
      mp = new PyNativeMethodObject(clazz.getMethod("sum", parameterTypes), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("sum", true), mp);
      mp = new PyNativeMethodObject(clazz.getMethod("iter", parameterTypes), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("iter", true), mp);
      mp = new PyNativeMethodObject(clazz.getMethod("len", parameterTypes), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("len", true), mp);
      mp = new PyNativeMethodObject(clazz.getMethod("sorted", PyTupleObject.class, PyDictObject.class), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("sorted", true), mp);
      mp = new PyNativeMethodObject(clazz.getMethod("max", PyTupleObject.class, PyDictObject.class), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("max", true), mp);
      mp = new PyNativeMethodObject(clazz.getMethod("min", PyTupleObject.class, PyDictObject.class), true);
      dict.put(PyUnicodeObject.getOrCreateFromInternStringPool("min", true), mp);
    } catch (NoSuchMethodException | PyException ignore) {
    }
  }

  public static PyObject print(PyTupleObject args, PyDictObject kwArgs) throws PyException, IOException {
    // do error checking
    PyObject std;
    PyObject end;
    if (kwArgs == null) {
      std = Sys.stdout;
      end = PyUnicodeObject.getOrCreateFromInternStringPool("\n", true);
    } else {
      std = kwArgs.getOrDefault(PyUnicodeObject.getOrCreateFromInternStringPool("std", true),
          Sys.stdout);
      end = kwArgs.getOrDefault(PyUnicodeObject.getOrCreateFromInternStringPool("end", true),
          PyUnicodeObject.getOrCreateFromInternStringPool("\n", true));
    }
    if (!(std instanceof PyFileStreamObject stream)) {
      throw new PyTypeNotMatch("std require stdout PyFileStreamObject");
    }
    if (!(end instanceof PyUnicodeObject uni)) {
      throw new PyTypeNotMatch("end require stdout PyUnicodeObject");
    }
    TypeDoIterate iterator = args.getIterator();
    PyObject o;
    o = iterator.next();
    while (o != BuiltIn.PyExcStopIteration) {
      stream.writeString(o.str().getData());
      if (iterator.hasNext())
        stream.writeString(" ");
      o = iterator.next();
    }
    stream.writeString(uni.getData());
    return BuiltIn.None;
  }

  public static PyObject iter(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof TypeIterable itr) {
        return (PyObject) itr.getIterator();
      }
      throw new PyException("object " + object.repr() + " can not be an iterator");
    }
    throw new PyException("iter function require only 1 argument");
  }

  public static PyObject sum(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject o = args.get(0);
      if (o instanceof TypeIterable itr) {
        TypeDoIterate iterator = itr.getIterator();
        PyObject result = new PyLongObject(0);
        while (iterator.hasNext()) {
          result = Abstract.add(result, iterator.next());
          if (result == BuiltIn.notImplemented)
            throw new PyTypeError("can apply sum on " + o.repr());
        }
        return result;
      }
    }
    throw new PyTypeError("sum only require one iterable argument");
  }

  public static PyObject len(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject o = args.get(0);
      if (o instanceof TypeIterable itr) {
        TypeDoIterate iterator = itr.getIterator();
        return new PyLongObject(iterator.size());
      } else if (o instanceof PySequenceMethods seq) {
        return seq.sqLength(null);
      } else if (o instanceof PyMappingMethods map) {
        return map.mpLength(null);
      }
    }
    throw new PyTypeError("sum only require one iterable argument");
  }

  public static PyObject sorted(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyListObject list = PyListType.getListFromIterable(args, kwArgs);
    list.sort(args, kwArgs);
    return list;
  }

  public static PyObject max(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyListObject list = PyListType.getListFromIterable(args, kwArgs);
    if (list.size() == 0)
      return BuiltIn.None;
    PyObject res = list.get(0);
    for (int i = 0; i < list.size(); i++) {
      if (res.richCompare(list.get(i), TypeRichCompare.Operator.Py_LE).isTrue()) {
        res = list.get(i);
      }
    }
    return res;
  }

  public static PyObject min(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyListObject list = PyListType.getListFromIterable(args, kwArgs);
    if (list.size() == 0)
      return BuiltIn.None;
    PyObject res = list.get(0);
    for (int i = 0; i < list.size(); i++) {
      if (res.richCompare(list.get(i), TypeRichCompare.Operator.Py_GT).isTrue()) {
        res = list.get(i);
      }
    }
    return res;
  }
}
