package org.jpvm.objects;

import org.jpvm.errors.*;
import org.jpvm.internal.NumberHelper;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeName;
import org.jpvm.objects.types.PyListType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PyListObject extends PyObject
    implements TypeIterable, PySequenceMethods, PyMappingMethods {

  public static PyObject type = new PyListType();

  private final List<PyObject> obItem;

  public PyListObject() {
    this(1);
    Class<? extends PyListObject> clazz = this.getClass();
    if (methods == null) {
      methods = new PyDictObject();
      try {
        PyUnicodeObject name = PyUnicodeObject.getOrCreateFromInternStringPool("extend", true);
        Method method = clazz.getMethod("extend", PyObject.class);
        PyMethodObject extend = new PyMethodObject(this, method, "extend");
        methods.put(name, extend);
      } catch (NoSuchMethodException ignore) {
      }
    }
  }

  public PyListObject(int size) {
    obItem = new ArrayList<>(size);
  }

  public static PyBoolObject check(PyObject o) {
    return new PyBoolObject(o == type);
  }

  public void app1(PyObject obj) {
    obItem.add(obj);
  }

  public void append(PyObject obj) {
    obItem.add(obj);
  }

  public void insert(int idx, PyObject obj) {
    obItem.add(idx, obj);
  }

  public boolean pop() {
    if (obItem.size() == 0)
      throw new IndexOutOfBoundsException("pop() PyListObject has no elements");
    return obItem.remove(obItem.size() - 1) != null;
  }

  public boolean remove(PyObject obj) {
    return obItem.remove(obj);
  }

  public void sort(boolean reverse) {
    if (reverse)
      Collections.reverse(obItem);
  }

  public PyObject get(int idx) {
    if (idx >= obItem.size())
      throw new IndexOutOfBoundsException();
    return obItem.get(idx);
  }

  public void set(int idx, PyObject o) {
    obItem.set(idx, o);
  }

  public int size() {
    return obItem.size();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (PyObject object : obItem) {
      builder.append(object.toString());
      builder.append(", ");
    }
    if (builder.length() > 2)
      builder.delete(builder.length() - 2, builder.length());
    builder.append("]");
    return builder.toString();
  }

  @Override
  public Object toJavaType() {
    return obItem;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public TypeDoIterate getIterator() {
    return new PyListItrObject();
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return super.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (PyObject object : obItem) {
      builder.append(object.str());
      builder.append(", ");
    }
    if (builder.length() > 2)
      builder.delete(builder.length() - 2, builder.length());
    builder.append("]");
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyUnicodeObject repr() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (PyObject object : obItem) {
      builder.append(object.repr());
      builder.append(", ");
    }
    if (builder.length() > 2)
      builder.delete(builder.length() - 2, builder.length());
    builder.append("]");
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (op == Operator.Py_EQ) {
      if (!(o instanceof PyListObject list))
        return BuiltIn.False;
      int s = size();
      if (s != list.size()) return BuiltIn.False;
      for (int i = 0; i < s; i++) {
        if (richCompare(list.get(i), Operator.Py_EQ).isFalse())
          return BuiltIn.False;
      }
      return BuiltIn.True;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
  }

  @Override
  public PyObject sqInplaceConcat(PyObject o) throws PyTypeNotMatch {
    if (!(o instanceof PyLongObject data)) {
      throw new PyTypeNotMatch("require type int");
    }
    int s = size();
    for (int i = 0; i < data.getData(); i++) {
      for (int j = 0; j < s; ++j)
        append(get(j));
    }
    return this;
  }

  @Override
  public PyDictObject getDict() {
    return super.getDict();
  }

  @Override
  public PyObject mpLength(PyObject o) {
    return new PyLongObject(size());
  }

  @Override
  public PyObject mpSubscript(PyObject o) throws PyIndexOutOfBound, PyKeyError {
    if (o instanceof PyNumberMethods num) {
      try {
        PyObject n = num.nbInt();
        int idx = (int) ((PyLongObject) n).getData();
        if (idx >= size())
          throw new PyIndexOutOfBound("index " + idx + " out of bound");
        return get(idx);
      } catch (PyNotImplemented ignored) {
      }

      try {
        PyObject index = num.index();
        int idx = (int) ((PyLongObject) index).getData();
        if (idx >= size())
          throw new PyIndexOutOfBound("index " + idx + " out of bound");
        return get(idx);
      } catch (PyNotImplemented ignored) {
      }
    } else if (o instanceof PySliceObject slice) {
      PyListObject list = slice.unpacked(this);
      assert list != null;
      PyListObject object = new PyListObject();
      for (int i = 0; i < list.size(); i++) {
        int idx = (int) ((PyLongObject) list.get(i)).getData();
        object.append(obItem.get(idx));
      }
      return object;
    }
    throw new PyKeyError("require slice or number object");
  }

  @Override
  public PyObject mpAssSubscript(PyObject key, PyObject val) throws PyKeyError {
    // means __delitem__
    if (null == val) {
      if (key instanceof PySliceObject slice) {
        PyListObject list = slice.unpacked(this);
        for (int i = 0; i < list.size(); i++) {
          int idx = (int) ((PyLongObject) list.get(i)).getData();
          obItem.remove(idx);
        }
      } else {
        Long n = NumberHelper.transformPyObject2Long(key);
        if (n == null)
          throw new PyKeyError("key " + key.str() + " is not a key for list");
        obItem.remove(n.intValue());
      }
    } else {
      // means __setitem__
      if (key instanceof PySliceObject slice) {
        PyListObject list = slice.unpacked(this);
        for (int i = 0; i < list.size(); i++) {
          int idx = (int) ((PyLongObject) list.get(i)).getData();
          obItem.set(idx, val);
        }
      } else {
        Long n = NumberHelper.transformPyObject2Long(key);
        if (n == null)
          throw new PyKeyError("key " + key.str() + " is not a key for list");
        obItem.set(n.intValue(), val);
      }
    }
    throw new PyKeyError("key " + key.str() + " is not a key for list");
  }

  @Override
  public PyObject sqLength(PyObject o) {
    return new PyLongObject(size());
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyTypeNotMatch {
    if (!(o instanceof PyListObject l))
      throw new PyTypeNotMatch("list concat require data type list");
    PyListObject list = new PyListObject();
    for (PyObject object : obItem) {
      list.append(object);
    }
    for (int i = 0; i < l.size(); ++i)
      list.append(l.get(i));
    return list;
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyTypeNotMatch {
    PyListObject list = new PyListObject();
    Long n = NumberHelper.transformPyObject2Long(o);
    if (n == null)
      throw new PyTypeNotMatch("require PyNumberMethods type");
    for (int i = 0; i < n.intValue(); ++i) {
      for (PyObject object : obItem) {
        list.append(object);
      }
    }
    return list;
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyTypeNotMatch {
    if (o instanceof PyLongObject) {
      Long n = NumberHelper.transformPyObject2Long(o);
      if (n == null)
        throw new PyTypeNotMatch("require PyNumberMethods type");
      return get(n.intValue());
    } else if (o instanceof PySliceObject slice) {
      PyListObject idx = slice.unpacked(this);
      PyListObject result = new PyListObject();
      for (int i = 0; i < idx.size(); i++) {
        int index = (int) ((PyLongObject) idx.get(i)).getData();
        result.append(get(index));
      }
      return result;
    }
    throw new PyTypeNotMatch("require PyNumberMethods type");
  }

  @Override
  public PyObject sqAssItem(PyObject key, PyObject val) throws PyTypeNotMatch {
    Long n = NumberHelper.transformPyObject2Long(key);
    if (n == null)
      throw new PyTypeNotMatch("require PyNumberMethods type");
    set(n.intValue(), val);
    return BuiltIn.None;
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyUnsupportedOperator {
    for (PyObject object : obItem) {
      if (object.richCompare(o, Operator.Py_EQ).isTrue())
        return BuiltIn.True;
    }
    return BuiltIn.False;
  }

  @Override
  public PyObject sqInplaceRepeat(PyObject o) throws PyTypeNotMatch {
    Long n = NumberHelper.transformPyObject2Long(o);
    if (n == null)
      throw new PyTypeNotMatch("require PyNumberMethods type");
    int size = size();
    for (int i = 0; i < n.intValue(); ++i) {
      for (int j = 0; j < size; ++j)
        obItem.add(obItem.get(j));
    }
    return this;
  }

  public PyListObject extend(PyObject o) throws PyTypeNotMatch {
    if (o instanceof PyListObject list) {
      this.addAll(list);
    }
    throw new PyTypeNotMatch("PyListObject extend require one PyListObject argument");
  }

  public void addAll(PyListObject o) {
    for (int i = 0; i < o.size(); i++)
      append(o.get(i));
  }

  public static class PyListItrType extends PyTypeType implements TypeName {
    public PyListItrType() {
      name = "list_iterator";
    }

  }

  public class PyListItrObject extends PyObject implements TypeDoIterate {

    public static PyObject type = new PyListItrType();
    private int idx;

    public PyListItrObject() {
      idx = 0;
    }

    @Override
    public PyObject next() {
      if (idx < obItem.size()) {
        return obItem.get(idx++);
      }
      return BuiltIn.PyExcStopIteration;
    }

    @Override
    public PyObject get(int idx) throws PyIndexOutOfBound {
      if (idx >= size())
        throw new PyIndexOutOfBound("index " + idx + " out of bound");
      return obItem.get(idx);
    }

    @Override
    public int size() {
      return PyListObject.this.size();
    }

    @Override
    public boolean hasNext() {
      return idx < size();
    }
  }
}
