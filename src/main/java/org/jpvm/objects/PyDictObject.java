package org.jpvm.objects;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyDictType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

public class PyDictObject extends PyObject
    implements PyMappingMethods, PySequenceMethods, TypeIterable {

  public static final PyTypeType type = new PyDictType();

  private final Map<PyObject, PyObject> map;

  public PyDictObject() {
    this.map = new ConcurrentHashMap<>();
  }

  public PyObject put(PyObject key, PyObject val) throws PyException {
    assert key != null;
    assert val != null;
    try {
      return map.put(key, val);
    } catch (ConcurrentModificationException e) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "can not put new items while iterating");
      return null;
    }
  }

  public synchronized PyObject add(PyObject key, PyObject val) throws PyException {
    return put(key, val);
  }

  public synchronized void remove(PyObject key) {
    map.remove(key);
  }

  public PyObject get(PyObject key) {
    return map.get(key);
  }

  public PyObject getOrDefault(PyObject key, PyObject val) {
    return map.getOrDefault(key, val);
  }

  public synchronized void addAll(PyDictObject dict) {
    if (dict != null) map.putAll(dict.getMap());
  }

  public Map<PyObject, PyObject> getMap() {
    return map;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{");

    map.forEach(
        (x, y) -> {
          builder.append(x.repr());
          builder.append(": ");
          builder.append(y.repr());
          builder.append(", ");
        });
    if (builder.length() > 2) builder.delete(builder.length() - 2, builder.length());
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

  public int size() {
    return map.size();
  }

  @PyClassMethod
  public PyObject items(PyTupleObject args, PyDictObject kwArgs) {
    return new PyDictItemsObject(map);
  }

  @PyClassMethod
  public PyObject keys(PyTupleObject args, PyDictObject kwArgs) {
    return new PyDictKeysObject(map);
  }

  @PyClassMethod
  public PyObject values(PyTupleObject args, PyDictObject kwArgs) {
    return new PyDictValuesObject(map);
  }

  @PyClassMethod
  public PyObject get(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      return map.getOrDefault(args.get(0), BuiltIn.None);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "dict get method only require one argument");
    return null;
  }

  @PyClassMethod
  public PyObject copy(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyDictObject result = new PyDictObject();
    result.addAll(this);
    return result;
  }

  @PyClassMethod
  public synchronized PyObject pop(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject remove = map.remove(args.get(0));
      if (remove == null) return BuiltIn.None;
      return remove;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "dict pop method only require one argument");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject clear(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    map.clear();
    return BuiltIn.None;
  }

  @PyClassMethod
  public synchronized PyObject update(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyDictObject o) {
        addAll(o);
        return BuiltIn.None;
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.Exception, "dict update method only require one PyDictObject object");
    return null;
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (op == Operator.Py_EQ) {
      if (o instanceof PyDictObject dict) {
        if (map.equals(dict.toJavaType())) return BuiltIn.True;
        return BuiltIn.False;
      }
      return BuiltIn.False;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
    return null;
  }

  @Override
  public PyObject mpLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(map.size());
  }

  @Override
  public PyObject mpSubscript(PyObject o)
      throws PyIndexOutOfBound, PyKeyError, PyTypeNotMatch, PyNotImplemented {
    return map.getOrDefault(o, BuiltIn.None);
  }

  @Override
  public synchronized PyObject mpAssSubscript(PyObject key, PyObject val)
      throws PyKeyError, PyNotImplemented {
    if (null == val) map.remove(key);
    else map.put(key, val);
    return BuiltIn.None;
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(map.size());
  }

  @Override
  public synchronized PyObject sqConcat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqConcat(o);
  }

  @Override
  public synchronized PyObject sqRepeat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqRepeat(o);
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyException {
    return PySequenceMethods.super.sqItem(o);
  }

  @Override
  public synchronized PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    return PySequenceMethods.super.sqAssItem(key, val);
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (map.containsKey(o)) return BuiltIn.True;
    return BuiltIn.False;
  }

  @Override
  public synchronized PyObject sqInplaceConcat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqInplaceConcat(o);
  }

  @Override
  public synchronized PyObject sqInplaceRepeat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqInplaceRepeat(o);
  }

  @Override
  public TypeDoIterate getIterator() throws PyNotImplemented {
    return new PyDictItrObject();
  }

  public PyObject items() {
    return new PyDictItemsObject(map);
  }

  public PyObject keys() {
    return new PyDictKeysObject(map);
  }

  public PyObject values() {
    return new PyDictValuesObject(map);
  }

  public static class PyDictItrType extends PyTypeType {
    public PyDictItrType() {
      super(PyDictItrObject.class);
      name = "dict_key_iterator";
    }
  }

  public static class PyDictValuesType extends PyTypeType {

    public PyDictValuesType() {
      super(PyDictValuesObject.class);
      name = "dict_values";
    }
  }

  public static class PyDictValuesObject extends PyObject
      implements TypeIterable, PySequenceMethods {

    public static final PyTypeType type = new PyDictValuesType();
    private final Collection<PyObject> values;

    public PyDictValuesObject(Map<PyObject, PyObject> map) {
      values = map.values();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_values[");
      for (PyObject value : values) {
        builder.append(value.toString()).append(", ");
      }
      if (builder.length() >= 14) builder.delete(builder.length() - 2, builder.length());
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
    public TypeDoIterate getIterator() throws PyNotImplemented {
      return new PyDictValuesItrObject();
    }

    public static class PyDictValuesItrType extends PyTypeType {
      public PyDictValuesItrType() {
        super(PyDictValuesItrObject.class);
        name = "dict_value_iterator";
      }
    }

    public class PyDictValuesItrObject extends PyObject implements TypeDoIterate {

      public static final PyTypeType type = new PyDictValuesItrType();
      Iterator<PyObject> iterator;

      public PyDictValuesItrObject() {
        iterator = values.iterator();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext()) return iterator.next();
        return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
      }

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }
    }
  }

  public static class PyDictKeysType extends PyTypeType {

    public PyDictKeysType() {
      super(PyDictKeysObject.class);
      this.name = "dict_keys";
    }
  }

  public static class PyDictKeysObject extends PyObject
      implements PyNumberMethods, PySequenceMethods, TypeIterable {

    public static final PyTypeType type = new PyDictKeysType();
    private final Set<PyObject> set;

    public PyDictKeysObject(Map<PyObject, PyObject> map) {
      set = map.keySet();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_keys{");
      for (PyObject value : set) {
        builder.append(value.toString()).append(", ");
      }
      if (builder.length() > 10) builder.delete(builder.length() - 2, builder.length());
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

    /** not implemented in the current version */
    @Override
    public PyObject sub(PyObject o) throws PyException {
      return PyNumberMethods.super.sub(o);
    }

    /** not implemented in the current version */
    @Override
    public PyObject and(PyObject o) throws PyException {
      return PyNumberMethods.super.and(o);
    }

    /** not implemented in the current version */
    @Override
    public PyObject xor(PyObject o) throws PyException {
      return PyNumberMethods.super.xor(o);
    }

    /** not implemented in the current version */
    @Override
    public PyObject or(PyObject o) throws PyException {
      return PyNumberMethods.super.or(o);
    }

    @Override
    public PyObject sqLength(PyObject o) throws PyNotImplemented {
      return new PyLongObject(set.size());
    }

    @Override
    public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (set.contains(o)) return BuiltIn.True;
      return BuiltIn.False;
    }

    @Override
    public TypeDoIterate getIterator() throws PyNotImplemented {
      return new PyDictKeysItrObject();
    }

    public static class PyDictKeysItrType extends PyTypeType {
      public PyDictKeysItrType() {
        super(PyDictKeysItrObject.class);
        name = "dict_value_iterator";
      }
    }

    private class PyDictKeysItrObject extends PyObject implements TypeDoIterate {
      public static final PyTypeType type = new PyDictKeysItrType();
      Iterator<PyObject> iterator;

      public PyDictKeysItrObject() {
        iterator = set.iterator();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext()) return iterator.next();
        return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
      }

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }
    }
  }

  public static class PyDictItemsType extends PyTypeType {
    public PyDictItemsType() {
      super(PyDictItemsObject.class);
      name = "dict_items";
    }
  }

  public static class PyDictItemsObject extends PyObject
      implements TypeIterable, PyNumberMethods, PySequenceMethods {
    public static final PyTypeType type = new PyDictItemsType();

    private final Map<PyObject, PyObject> map;

    public PyDictItemsObject(Map<PyObject, PyObject> map) {
      this.map = map;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_items[");
      map.forEach(
          (key, val) -> {
            builder.append("(").append(key).append(", ").append(val).append("), ");
          });
      if (builder.length() > 11) builder.delete(builder.length() - 2, builder.length());
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
    public TypeDoIterate getIterator() throws PyNotImplemented {
      return new PyDictItemsItrObject();
    }

    @Override
    public Object toJavaType() {
      return map;
    }

    @Override
    public PyObject sub(PyObject o) throws PyException {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach(
            (x, y) -> {
              if (item.map.containsKey(x)) ret.map.remove(x);
            });
        return ret;
      }
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type PyDictItemsObject");
      return null;
    }

    @Override
    public PyObject and(PyObject o) throws PyException {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach(
            (x, y) -> {
              if (item.map.containsKey(x)) ret.map.put(x, y);
            });
        item.map.forEach(
            (x, y) -> {
              if (map.containsKey(x)) ret.map.put(x, y);
            });
        return ret;
      }
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type PyDictItemsObject");
      return null;
    }

    @Override
    public PyObject xor(PyObject o) throws PyException {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach(
            (x, y) -> {
              if (!item.map.containsKey(x)) ret.map.put(x, y);
            });
        item.map.forEach(
            (x, y) -> {
              if (!map.containsKey(x)) ret.map.put(x, y);
            });
        return ret;
      }
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type PyDictItemsObject");
      return null;
    }

    @Override
    public PyObject or(PyObject o) throws PyException {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        ret.map.putAll(map);
        ret.map.putAll(item.map);
        return ret;
      }
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type PyDictItemsObject");
      return null;
    }

    @Override
    public PyObject sqLength(PyObject o) throws PyNotImplemented {
      return new PyLongObject(map.size());
    }

    @Override
    public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (map.containsKey(o)) return BuiltIn.True;
      return BuiltIn.False;
    }

    public static class PyDictItemsItrType extends PyTypeType {
      public PyDictItemsItrType() {
        super(PyDictItemsItrObject.class);
        name = "dict_items";
      }
    }

    public class PyDictItemsItrObject extends PyObject implements TypeDoIterate {
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
        return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
      }

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }
    }
  }

  public class PyDictItrObject extends PyObject implements TypeDoIterate {
    public static final PyTypeType type = new PyDictItrType();
    Iterator<PyObject> iterator;

    public PyDictItrObject() {
      iterator = map.keySet().iterator();
    }

    @Override
    public PyObject next() throws PyException {
      if (iterator.hasNext()) return iterator.next();
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }
  }
}
