package org.jpvm.objects;

import org.jpvm.errors.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyDictType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

import java.util.*;

public class PyDictObject extends PyObject implements PyMappingMethods,
    PySequenceMethods, TypeIterable {

  public static PyObject type = new PyDictType();

  private final Map<PyObject, PyObject> map;

  public PyDictObject() {
    this.map = new HashMap<>();
  }

  public PyObject put(PyObject key, PyObject val) {
    return map.put(key, val);
  }

  public PyObject add(PyObject key, PyObject val) {
    return map.put(key, val);
  }

  public PyObject get(PyObject key) {
    return map.get(key);
  }

  public void addAll(PyDictObject dict) {
    map.putAll(dict.getMap());
  }

  public Map<PyObject, PyObject> getMap() {
    return map;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{");

    map.forEach((x, y) -> {
      builder.append(x.toString());
      builder.append(": ");
      builder.append(y.toString());
      builder.append(", ");
    });
    if (builder.length() > 2)
      builder.delete(builder.length() - 2, builder.length());
    builder.append("}");
    return builder.toString();
  }

  public boolean containKey(PyObject key) {
    return map.containsKey(key);
  }

  @Override
  public Object toJavaType() {
    return map;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (op == Operator.PY_EQ) {
      if (o instanceof PyDictObject dict) {
        if (map.equals(dict.toJavaType()))
          return BuiltIn.True;
        return BuiltIn.False;
      }
      return BuiltIn.False;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
  }


  @Override
  public PyObject mpLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(map.size());
  }

  @Override
  public PyObject mpSubscript(PyObject o) throws PyIndexOutOfBound, PyKeyError, PyTypeNotMatch, PyNotImplemented {
    return map.getOrDefault(o, BuiltIn.None);
  }

  @Override
  public PyObject mpAssSubscript(PyObject key, PyObject val) throws PyKeyError, PyNotImplemented {
    if (null == val)
      map.remove(key);
    else
      map.put(key, val);
    return BuiltIn.None;
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(map.size());
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqConcat(o);
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqRepeat(o);
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqItem(o);
  }

  @Override
  public PyObject sqAssItem(PyObject key, PyObject val) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqAssItem(key, val);
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (map.containsKey(o))
      return BuiltIn.True;
    return BuiltIn.False;
  }

  @Override
  public PyObject sqInplaceConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqInplaceConcat(o);
  }

  @Override
  public PyObject sqInplaceRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    return PySequenceMethods.super.sqInplaceRepeat(o);
  }

  @Override
  public PyObject getIterator() throws PyNotImplemented {
    return new PyDictItrObject();
  }

  public static class PyDictItrType extends PyTypeType {
    private final PyUnicodeObject name;

    public PyDictItrType() {
      this.name = new PyUnicodeObject("dict_key_iterator");
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return name;
    }
  }

  public static class PyDictValuesType extends PyTypeType {
    private final PyUnicodeObject name;

    public PyDictValuesType() {
      this.name = new PyUnicodeObject("dict_values");
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return name;
    }
  }

  public static class PyDictValuesObject extends PyObject
      implements TypeIterable, PySequenceMethods {

    public static PyObject type = new PyDictValuesType();
    private final Collection<PyObject> values;

    public PyDictValuesObject(Map<PyObject, PyObject> map) {
      values = map.values();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_values[");
      for (PyObject value : values) {
        builder.append(value.toString())
            .append(", ");
      }
      if (builder.length() >= 14)
        builder.delete(builder.length() - 2, builder.length());
      builder.append("]");
      return builder.toString();
    }

    @Override
    public PyObject getType() {
      return type;
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return type.getTypeName();
    }

    @Override
    public PyUnicodeObject str() {
      return new PyUnicodeObject(toString());
    }

    @Override
    public PyUnicodeObject repr() {
      return new PyUnicodeObject(toString());
    }

    @Override
    public PyObject sqLength(PyObject o) throws PyNotImplemented {
      return new PyLongObject(values.size());
    }

    @Override
    public PyObject getIterator() throws PyNotImplemented {
      return new PyDictValuesItrObject();
    }

    public static class PyDictValuesItrType extends PyObject {
      private final PyUnicodeObject name;

      public PyDictValuesItrType() {
        name = new PyUnicodeObject("dict_value_iterator");
      }

      @Override
      public PyUnicodeObject getTypeName() {
        return name;
      }
    }

    public class PyDictValuesItrObject extends PyObject
        implements TypeDoIterate {

      public static PyObject type = new PyDictValuesItrType();
      Iterator<PyObject> iterator;

      public PyDictValuesItrObject() {
        iterator = values.iterator();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext())
          return iterator.next();
        return BuiltIn.PyExcStopIteration;
      }
    }
  }

  public static class PyDictKeysType extends PyTypeType {
    private final PyUnicodeObject name;

    public PyDictKeysType() {
      this.name = new PyUnicodeObject("dict_keys");
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return name;
    }
  }

  public static class PyDictKeysObject extends PyObject
      implements PyNumberMethods, PySequenceMethods, TypeIterable {

    public static PyObject type = new PyDictKeysType();
    private final Set<PyObject> set;

    public PyDictKeysObject(Map<PyObject, PyObject> map) {
      set = map.keySet();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_keys{");
      for (PyObject value : set) {
        builder.append(value.toString())
            .append(", ");
      }
      if (builder.length() > 10)
        builder.delete(builder.length() - 2, builder.length());
      builder.append("}");
      return builder.toString();
    }

    @Override
    public PyObject getType() {
      return super.getType();
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return super.getTypeName();
    }

    @Override
    public PyUnicodeObject str() {
      return new PyUnicodeObject(toString());
    }

    @Override
    public PyUnicodeObject repr() {
      return new PyUnicodeObject(toString());
    }

    /**
     * not implemented in the current version
     */
    @Override
    public PyObject sub(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      return PyNumberMethods.super.sub(o);
    }

    /**
     * not implemented in the current version
     */
    @Override
    public PyObject and(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      return PyNumberMethods.super.and(o);
    }

    /**
     * not implemented in the current version
     */
    @Override
    public PyObject xor(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      return PyNumberMethods.super.xor(o);
    }

    /**
     * not implemented in the current version
     */
    @Override
    public PyObject or(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      return PyNumberMethods.super.or(o);
    }

    @Override
    public PyObject sqLength(PyObject o) throws PyNotImplemented {
      return new PyLongObject(set.size());
    }

    @Override
    public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (set.contains(o))
        return BuiltIn.True;
      return BuiltIn.False;
    }

    @Override
    public PyObject getIterator() throws PyNotImplemented {
      return new PyDictKeysItrObject();
    }

    public static class PyDictKeysItrType extends PyObject {
      private final PyUnicodeObject name;

      public PyDictKeysItrType() {
        name = new PyUnicodeObject("dict_value_iterator");
      }

      @Override
      public PyUnicodeObject getTypeName() {
        return name;
      }
    }

    private class PyDictKeysItrObject extends PyObject
        implements TypeDoIterate {
      public static PyObject type = new PyDictKeysItrType();
      Iterator<PyObject> iterator;

      public PyDictKeysItrObject() {
        iterator = set.iterator();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext())
          return iterator.next();
        return BuiltIn.PyExcStopIteration;
      }
    }
  }

  public static class PyDictItemsType extends PyTypeType {
    private final PyUnicodeObject name;

    public PyDictItemsType() {
      this.name = new PyUnicodeObject("dict_items");
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return name;
    }


  }

  public static class PyDictItemsObject extends PyObject
      implements TypeIterable, PyNumberMethods, PySequenceMethods {
    public static PyObject type = new PyDictItemsType();

    private final Map<PyObject, PyObject> map;

    public PyDictItemsObject(Map<PyObject, PyObject> map) {
      this.map = map;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_items[");
      map.forEach((key, val) -> {
        builder.append("(")
            .append(key).append(", ")
            .append(val).append("), ");
      });
      if (builder.length() > 11)
        builder.delete(builder.length() - 2, builder.length());
      builder.append("]");
      return builder.toString();
    }

    @Override
    public PyObject getType() {
      return type;
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return type.getTypeName();
    }

    @Override
    public PyUnicodeObject str() {
      return new PyUnicodeObject(toString());
    }

    @Override
    public PyUnicodeObject repr() {
      return new PyUnicodeObject(toString());
    }

    @Override
    public PyObject getIterator() throws PyNotImplemented {
      return new PyDictItemsItrObject();
    }

    @Override
    public Object toJavaType() {
      return map;
    }

    @Override
    public PyObject sub(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach((x, y) -> {
          if (item.map.containsKey(x))
            ret.map.remove(x);
        });
        return ret;
      }
      throw new PyTypeNotMatch("require type PyDictItemsObject");
    }

    @Override
    public PyObject and(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach((x, y) -> {
          if (item.map.containsKey(x))
            ret.map.put(x, y);
        });
        item.map.forEach((x, y) -> {
          if (map.containsKey(x))
            ret.map.put(x, y);
        });
        return ret;
      }
      throw new PyTypeNotMatch("require type PyDictItemsObject");
    }

    @Override
    public PyObject xor(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach((x, y) -> {
          if (!item.map.containsKey(x))
            ret.map.put(x, y);
        });
        item.map.forEach((x, y) -> {
          if (!map.containsKey(x))
            ret.map.put(x, y);
        });
        return ret;
      }
      throw new PyTypeNotMatch("require type PyDictItemsObject");
    }

    @Override
    public PyObject or(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        ret.map.putAll(map);
        ret.map.putAll(item.map);
        return ret;
      }
      throw new PyTypeNotMatch("require type PyDictItemsObject");
    }

    @Override
    public PyObject sqLength(PyObject o) throws PyNotImplemented {
      return new PyLongObject(map.size());
    }

    @Override
    public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (map.containsKey(o))
        return BuiltIn.True;
      return BuiltIn.False;
    }

    public static class PyDictItemsItrType extends PyTypeType {
      private final PyUnicodeObject name;

      public PyDictItemsItrType() {
        this.name = new PyUnicodeObject("dict_items");
      }

      @Override
      public PyUnicodeObject getTypeName() {
        return name;
      }
    }

    public class PyDictItemsItrObject extends PyObject
        implements TypeDoIterate {
      private final PyObject type = new PyDictItemsItrType();
      private final Iterator<Map.Entry<PyObject, PyObject>> iterator;

      public PyDictItemsItrObject() {
        iterator = map.entrySet().iterator();
      }

      @Override
      public PyObject getType() {
        return type;
      }

      @Override
      public PyUnicodeObject getTypeName() {
        return type.getTypeName();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext()) {
          Map.Entry<PyObject, PyObject> n = iterator.next();
          PyTupleObject object = new PyTupleObject(2);
          object.set(0, n.getKey());
          object.set(1, n.getValue());
          return object;
        }
        return BuiltIn.PyExcStopIteration;
      }
    }
  }

  public class PyDictItrObject extends PyObject implements TypeDoIterate {
    public static PyObject type = new PyDictItrType();
    Iterator<PyObject> iterator;

    public PyDictItrObject() {
      iterator = map.keySet().iterator();
    }

    @Override
    public PyObject next() {
      if (iterator.hasNext())
        return iterator.next();
      return BuiltIn.PyExcStopIteration;
    }
  }
}
