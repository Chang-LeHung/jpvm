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

  public static final PyTypeType type = PyDictType.getInstance();

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

  public PyObject add(PyObject key, PyObject val) throws PyException {
    return put(key, val);
  }

  public void remove(PyObject key) {
    map.remove(key);
  }

  public PyObject get(PyObject key) {
    return map.get(key);
  }

  public PyObject getOrDefault(PyObject key, PyObject val) {
    return map.getOrDefault(key, val);
  }

  public void addAll(PyDictObject dict) {
    if (dict != null)
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
      builder.append(x.repr());
      builder.append(": ");
      builder.append(y.repr());
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
  public PyObject pop(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject remove = map.remove(args.get(0));
      if (remove == null)
        return BuiltIn.None;
      return remove;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "dict pop method only require one argument");
    return null;
  }

  @PyClassMethod
  public PyObject clear(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    map.clear();
    return BuiltIn.None;
  }

  @PyClassMethod
  public PyObject update(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyDictObject o) {
        addAll(o);
        return BuiltIn.None;
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception,
        "dict update method only require one PyDictObject object");
    return null;
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (op == Operator.Py_EQ) {
      if (o instanceof PyDictObject dict) {
        if (map.equals(dict.toJavaType()))
          return BuiltIn.True;
        return BuiltIn.False;
      }
      return BuiltIn.False;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
    return null;
  }

  @Override
  public PyObject mpLength() throws PyNotImplemented {
    return new PyLongObject(map.size());
  }

  @Override
  public PyObject __len__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyMappingMethods.super.__len__(args, kwArgs);
  }

  @Override
  public PyObject mpSubscript(PyObject o)
      throws PyIndexOutOfBound, PyKeyError, PyTypeNotMatch, PyNotImplemented {
    return map.getOrDefault(o, BuiltIn.None);
  }

  @Override
  public PyObject __getitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyMappingMethods.super.__getitem__(args, kwArgs);
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
  public PyObject __setitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyMappingMethods.super.__setitem__(args, kwArgs);
  }

  @Override
  public PyObject __delitem__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return PyMappingMethods.super.__delitem__(args, kwArgs);
  }

  @Override
  public PyObject sqLength() throws PyNotImplemented {
    return new PyLongObject(map.size());
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqConcat(o);
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqRepeat(o);
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyException {
    return PySequenceMethods.super.sqItem(o);
  }

  @Override
  public PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    return PySequenceMethods.super.sqAssItem(key, val);
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (map.containsKey(o))
      return BuiltIn.True;
    return BuiltIn.False;
  }

  @Override
  public PyObject sqInplaceConcat(PyObject o) throws PyException {
    return PySequenceMethods.super.sqInplaceConcat(o);
  }

  @Override
  public PyObject sqInplaceRepeat(PyObject o) throws PyException {
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

  @Override
  public PyBoolObject isHashable() {
    return BuiltIn.False;
  }

  public static class PyDictItrType extends PyTypeType {
    private PyDictItrType() {
      super(PyDictItrObject.class);
      name = "dict_key_iterator";
    }

    public static final class SelfHolder {
      public static final PyDictItrType self = new PyDictItrType();
    }

    public static PyDictItrType getInstance() {
      return SelfHolder.self;
    }
  }

  public static class PyDictValuesType extends PyTypeType {

    private PyDictValuesType() {
      super(PyDictValuesObject.class);
      name = "dict_values";
    }

    public static final class SelfHolder {
      public static final PyDictValuesType self = new PyDictValuesType();
    }

    public static PyDictValuesType getInstance() {
      return SelfHolder.self;
    }
  }

  public static class PyDictValuesObject extends PyObject
      implements TypeIterable, PySequenceMethods {

    public static final PyTypeType type = PyDictValuesType.getInstance();
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
    public PyObject sqLength() throws PyNotImplemented {
      return new PyLongObject(values.size());
    }

    @Override
    public TypeDoIterate getIterator() throws PyNotImplemented {
      return new PyDictValuesItrObject();
    }

    public static class PyDictValuesItrType extends PyTypeType {
      private PyDictValuesItrType() {
        super(PyDictValuesItrObject.class);
        name = "dict_value_iterator";
      }

      public static final class SelfHolder {
        public static final PyDictValuesItrType self = new PyDictValuesItrType();
      }

      public static PyDictValuesItrType getInstance() {
        return SelfHolder.self;
      }
    }

    public class PyDictValuesItrObject extends PyObject implements TypeDoIterate {

      public static final PyTypeType type = PyDictValuesItrType.getInstance();
      Iterator<PyObject> iterator;

      public PyDictValuesItrObject() {
        iterator = values.iterator();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext())
          return iterator.next();
        return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
      }

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }
    }
  }

  public static class PyDictKeysType extends PyTypeType {

    private PyDictKeysType() {
      super(PyDictKeysObject.class);
      this.name = "dict_keys";
    }

    public static final class SelfHolder {
      public static final PyDictKeysType self = new PyDictKeysType();
    }

    public static PyDictKeysType getInstance() {
      return SelfHolder.self;
    }
  }

  public static class PyDictKeysObject extends PyObject
      implements PyNumberMethods, PySequenceMethods, TypeIterable {

    public static final PyTypeType type = PyDictKeysType.getInstance();
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

    @Override
    public PyObject __add__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__add__(args, kwArgs);
    }

    /** not implemented in the current version */
    @Override
    public PyObject sub(PyObject o) throws PyException {
      return PyNumberMethods.super.sub(o);
    }

    @Override
    public PyObject __mul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__mul__(args, kwArgs);
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
    public PyObject __iadd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__iadd__(args, kwArgs);
    }

    @Override
    public PyObject __imul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__imul__(args, kwArgs);
    }

    @Override
    public PyObject sqLength() throws PyNotImplemented {
      return new PyLongObject(set.size());
    }

    @Override
    public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (set.contains(o))
        return BuiltIn.True;
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
      public static final PyTypeType type = PyDictKeysType.getInstance();
      Iterator<PyObject> iterator;

      public PyDictKeysItrObject() {
        iterator = set.iterator();
      }

      @Override
      public PyObject next() throws PyException {
        if (iterator.hasNext())
          return iterator.next();
        return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
      }

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }
    }
  }

  public static class PyDictItemsType extends PyTypeType {
    private PyDictItemsType() {
      super(PyDictItemsObject.class);
      name = "dict_items";
    }

    public static final class SelfHolder {
      public static final PyDictItemsType self = new PyDictItemsType();
    }

    public static PyDictItemsType getInstance() {
      return SelfHolder.self;
    }
  }

  public static class PyDictItemsObject extends PyObject
      implements TypeIterable, PyNumberMethods, PySequenceMethods {
    public static final PyTypeType type = PyDictItemsType.getInstance();

    private final Map<PyObject, PyObject> map;

    public PyDictItemsObject(Map<PyObject, PyObject> map) {
      this.map = map;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("dict_items[");
      map.forEach((key, val) -> {
        builder.append("(").append(key).append(", ").append(val).append("), ");
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
    public TypeDoIterate getIterator() throws PyNotImplemented {
      return new PyDictItemsItrObject();
    }

    @Override
    public Object toJavaType() {
      return map;
    }

    @Override
    public PyObject __add__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__add__(args, kwArgs);
    }

    @Override
    public PyObject sub(PyObject o) throws PyException {
      if (o instanceof PyDictItemsObject item) {
        PyDictItemsObject ret = new PyDictItemsObject(new HashMap<>());
        map.forEach((x, y) -> {
          if (item.map.containsKey(x))
            ret.map.remove(x);
        });
        return ret;
      }
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type PyDictItemsObject");
      return null;
    }

    @Override
    public PyObject __mul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__mul__(args, kwArgs);
    }

    @Override
    public PyObject and(PyObject o) throws PyException {
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
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type PyDictItemsObject");
      return null;
    }

    @Override
    public PyObject xor(PyObject o) throws PyException {
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
    public PyObject __iadd__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__iadd__(args, kwArgs);
    }

    @Override
    public PyObject __imul__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
      return PyNumberMethods.super.__imul__(args, kwArgs);
    }

    @Override
    public PyObject sqLength() throws PyNotImplemented {
      return new PyLongObject(map.size());
    }

    @Override
    public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
      if (map.containsKey(o))
        return BuiltIn.True;
      return BuiltIn.False;
    }

    public static class PyDictItemsItrType extends PyTypeType {
      private PyDictItemsItrType() {
        super(PyDictItemsItrObject.class);
        name = "dict_items";
      }

      public static final class SelfHolder {
        public static final PyDictItemsItrType self = new PyDictItemsItrType();
      }

      public static PyDictItemsItrType getInstance() {
        return SelfHolder.self;
      }
    }

    public class PyDictItemsItrObject extends PyObject implements TypeDoIterate {
      private final PyObject type = PyDictItemsItrType.getInstance();
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
    public static final PyTypeType type = PyDictItrType.getInstance();
    Iterator<PyObject> iterator;

    public PyDictItrObject() {
      iterator = map.keySet().iterator();
    }

    @Override
    public PyObject next() throws PyException {
      if (iterator.hasNext())
        return iterator.next();
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }
  }
}
