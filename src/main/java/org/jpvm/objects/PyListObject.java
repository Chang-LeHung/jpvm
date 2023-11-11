package org.jpvm.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.excptions.objs.PyException;
import org.jpvm.excptions.objs.PyTypeError;
import org.jpvm.excptions.objs.PyUnsupportedOperator;
import org.jpvm.internal.NumberHelper;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeName;
import org.jpvm.objects.types.PyListType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.vm.Abstract;
import org.jpvm.python.BuiltIn;

public class PyListObject extends PyObject
    implements TypeIterable, PySequenceMethods, PyMappingMethods {

  public static PyObject type = new PyListType();

  private final List<PyObject> obItem;

  public PyListObject() {
    this(1);
  }

  public PyListObject(int size) {
    obItem = new ArrayList<>(size);
  }

  public static PyBoolObject check(PyObject o) {
    if (o == type) return BuiltIn.True;
    return BuiltIn.False;
  }

  public synchronized void app1(PyObject obj) {
    obItem.add(obj);
  }

  public synchronized void append(PyObject obj) {
    obItem.add(obj);
  }

  public synchronized void add(PyObject obj) {
    app1(obj);
  }

  public synchronized void insert(int idx, PyObject obj) {
    obItem.add(idx, obj);
  }

  public synchronized PyObject pop() throws PyException {
    if (obItem.size() == 0) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.KeyError, "pop() PyListObject has no elements");
      return null;
    }
    return obItem.remove(obItem.size() - 1);
  }

  public synchronized boolean remove(PyObject obj) {
    return obItem.remove(obj);
  }

  public synchronized void sort(boolean reverse) {
    if (reverse) Collections.reverse(obItem);
  }

  public PyObject get(int idx) throws PyException {
    if (idx >= obItem.size()) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.KeyError, "get() PyListObject has no elements");
      return null;
    }
    return obItem.get(idx);
  }

  public synchronized void set(int idx, PyObject o) {
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
    if (builder.length() > 2) builder.delete(builder.length() - 2, builder.length());
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
      builder.append(object.repr());
      builder.append(", ");
    }
    if (builder.length() > 2) builder.delete(builder.length() - 2, builder.length());
    builder.append("]");
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (op == Operator.Py_EQ) {
      if (!(o instanceof PyListObject list)) return BuiltIn.False;
      int s = size();
      if (s != list.size()) return BuiltIn.False;
      for (int i = 0; i < s; i++) {
        if (get(i).richCompare(list.get(i), Operator.Py_EQ).isFalse()) return BuiltIn.False;
      }
      return BuiltIn.True;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
    return null;
  }

  @Override
  public synchronized PyObject sqInplaceConcat(PyObject o) throws PyException {
    if (!(o instanceof PyLongObject data)) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require type int");
      return null;
    }
    int s = size();
    for (int i = 0; i < data.getData(); i++) {
      for (int j = 0; j < s; ++j) append(get(j));
    }
    return this;
  }

  @Override
  public PyObject mpLength(PyObject o) {
    return new PyLongObject(size());
  }

  @Override
  public PyObject mpSubscript(PyObject o) throws PyException {
    if (o instanceof PyNumberMethods num) {
      try {
        PyObject n = num.nbInt();
        int idx = (int) ((PyLongObject) n).getData();
        if (idx >= size()) {
          PyErrorUtils.pyErrorFormat(PyErrorUtils.IndexError, "index " + idx + " out of bound");
          return null;
        }
        return get(idx);
      } catch (PyException ignored) {
        PyErrorUtils.cleanThreadException();
      }

      PyObject index = num.index();
      int idx = (int) ((PyLongObject) index).getData();
      if (idx >= size()) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.IndexError, "index " + idx + " out of bound");
        return null;
      }
      return get(idx);
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
    return null;
  }

  @Override
  public synchronized PyObject mpAssSubscript(PyObject key, PyObject val) throws PyException {
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
        if (n == null) {
          PyErrorUtils.pyErrorFormat(
              PyErrorUtils.KeyError, "key " + key.str() + " is not a key for list");
          return null;
        }
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
        if (n == null) {
          PyErrorUtils.pyErrorFormat(
              PyErrorUtils.KeyError, "key " + key.str() + " is not a key for list");
          return null;
        }
        obItem.set(n.intValue(), val);
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.KeyError, "key " + key.str() + " is not a key for list");
    return null;
  }

  @Override
  public PyObject sqLength(PyObject o) {
    return new PyLongObject(size());
  }

  @Override
  public synchronized PyObject sqConcat(PyObject o) throws PyException {
    if (!(o instanceof PyListObject l)) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "key " + "list concat require data type list");
      return null;
    }
    PyListObject list = new PyListObject();
    for (PyObject object : obItem) {
      list.append(object);
    }
    for (int i = 0; i < l.size(); ++i) list.append(l.get(i));
    return list;
  }

  @Override
  public synchronized PyObject sqRepeat(PyObject o) throws PyException {
    PyListObject list = new PyListObject();
    Long n = NumberHelper.transformPyObject2Long(o);
    if (n == null) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require PyNumberMethods type");
      return null;
    }
    for (int i = 0; i < n.intValue(); ++i) {
      for (PyObject object : obItem) {
        list.append(object);
      }
    }
    return list;
  }

  @Override
  public PyObject sqItem(PyObject o) throws PyException {
    if (o instanceof PyLongObject) {
      Long n = NumberHelper.transformPyObject2Long(o);
      if (n == null) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require PyNumberMethods type");
        return null;
      }
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
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require PyNumberMethods type");
    return null;
  }

  @Override
  public synchronized PyObject sqAssItem(PyObject key, PyObject val) throws PyException {
    if (val instanceof TypeIterable itr) {
      TypeDoIterate iterator = itr.getIterator();
      if (!(key instanceof PySliceObject slice)) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not a slicekey");
        return null;
      }
      PyListObject keys = slice.unpacked(this);
      Long step;
      if (((PySliceObject) key).getStep() instanceof PyNoneObject) {
        step = 1L;
      } else {
        step = ((PyLongObject) slice.getStep()).getData();
      }
      if (step == 0) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.KeyError, "slice step cannot be zero");
        return null;
      }
      int i = 0;
      int keyLast;
      if (keys.size() != 0) {
        keyLast = (int) ((PyLongObject) keys.get(keys.size() - 1)).getData();
      } else {
        keyLast = 0;
      }

      if (step != 1 && keys.size() != iterator.size()) {
        PyErrorUtils.pyErrorFormat(
            PyErrorUtils.Exception,
            "attempt to assign sequence of size "
                + iterator.size()
                + " to extended slice of size "
                + keys.size());
        return null;
      }
      while (iterator.hasNext()) {
        if (step == 1) {
          if (i < keys.size()) {
            set((int) ((PyLongObject) keys.get(i)).getData(), iterator.next());
          } else {
            insert(keyLast + i - keys.size() + 1, iterator.next());
          }
        } else {
          set((int) ((PyLongObject) keys.get(i)).getData(), iterator.next());
        }
        i++;
      }
      if (keys.size() > iterator.size()) {
        for (; i < keys.size(); i++) {
          pop();
        }
      }
      return BuiltIn.None;
    }
    Long n = NumberHelper.transformPyObject2Long(key);
    if (n == null) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require PyNumberMethods type");
      return null;
    }
    set(n.intValue(), val);
    return BuiltIn.None;
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyException {
    for (PyObject object : obItem) {
      if (object.richCompare(o, Operator.Py_EQ).isTrue()) return BuiltIn.True;
    }
    return BuiltIn.False;
  }

  @Override
  public synchronized PyObject sqInplaceRepeat(PyObject o) throws PyException {
    Long n = NumberHelper.transformPyObject2Long(o);
    if (n == null) {
      PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require PyNumberMethods type");
      return null;
    }
    int size = size();
    for (int i = 0; i < n.intValue(); ++i) {
      for (int j = 0; j < size; ++j) obItem.add(obItem.get(j));
    }
    return this;
  }

  @PyClassMethod
  public synchronized PyListObject extend(PyTupleObject args, PyDictObject kwArgs)
      throws PyException {
    if (args.size() == 1) {
      PyObject o = args.get(0);
      if (o instanceof PyListObject list) {
        this.addAll(list);
        return this;
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "PyListObject extend require one PyListObject argument");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject pop(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) return pop();
    else {
      PyObject object = args.get(0);
      if (object instanceof PyLongObject o) {
        return obItem.remove((int) o.getData());
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "pop require one or zero argument");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject append(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      append(args.get(0));
      return BuiltIn.True;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "list append method require one argument");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject remove(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      if (remove(args.get(0))) return BuiltIn.True;
      return BuiltIn.False;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "list append method require one argument");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject copy(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PyListObject result = new PyListObject();
    result.addAll(this);
    return result;
  }

  @PyClassMethod
  public PyObject index(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject o = args.get(0);
      for (int i = 0; i < obItem.size(); i++) {
        if (o.richCompare(obItem.get(i), Operator.Py_EQ).isTrue())
          return PyLongObject.getLongObject(i);
      }
      return PyLongObject.getLongObject(-1);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "list append method require one argument");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject reverse(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    Collections.reverse(obItem);
    return this;
  }

  @PyClassMethod
  public synchronized PyObject insert(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      PyObject idx = args.get(0);
      if (idx instanceof PyLongObject l) {
        PyObject val = args.get(1);
        insert((int) l.getData(), val);
        return BuiltIn.None;
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "list insert method require 2 method");
    return null;
  }

  @PyClassMethod
  public PyObject count(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    int cnt = 0;
    if (args.size() == 1) {
      PyObject o = args.get(0);
      for (PyObject object : obItem) {
        if (object.richCompare(o, Operator.Py_EQ).isTrue()) {
          cnt++;
        }
      }
      return new PyLongObject(cnt);
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "list count method require 1 method");
    return null;
  }

  @PyClassMethod
  public synchronized PyObject sort(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    var ref =
        new Object() {
          PyException error;
        };
    if (kwArgs == null) {
      obItem.sort(
          (x, y) -> {
            try {
              if (x.richCompare(y, Operator.Py_LT).isFalse()) {
                return 1;
              } else return -1;
            } catch (PyUnsupportedOperator e) {
              ref.error = new PyTypeError("'<' not supported between instances of 'str' and 'int'");
            } catch (PyException e) {
              ref.error = e;
            }
            return 0;
          });
    } else {
      PyObject func = kwArgs.get(PyUnicodeObject.getOrCreateFromInternStringPool("key", true));
      PyTupleObject tuple = new PyTupleObject(1);
      if (func != null) {
        obItem.sort(
            (x, y) -> {
              try {
                tuple.set(0, x);
                x = Abstract.abstractCall(func, null, tuple, null);
                tuple.set(0, y);
                y = Abstract.abstractCall(func, null, tuple, null);
              } catch (PyException e) {
                ref.error = new PyTypeError(e.getMessage());
              }
              try {
                if (x.richCompare(y, Operator.Py_LT).isFalse()) {
                  return 1;
                } else return -1;
              } catch (PyUnsupportedOperator e) {
                ref.error =
                    new PyTypeError("'<' not supported between instances of 'str' and 'int'");
              } catch (PyException e) {
                ref.error = e;
              }
              return 0;
            });
      }
    }
    if (ref.error == null) return BuiltIn.None;
    throw ref.error;
  }

  @PyClassMethod
  public synchronized PyObject clear(PyTupleObject args, PyDictObject kwArgs) {
    obItem.clear();
    return BuiltIn.None;
  }

  public synchronized void addAll(PyListObject o) throws PyException {
    for (int i = 0; i < o.size(); i++) append(o.get(i));
  }

  public static class PyListItrType extends PyTypeType implements TypeName {
    public PyListItrType() {
      super(PyListItrObject.class);
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
    public synchronized PyObject next() {
      if (idx < obItem.size()) {
        return obItem.get(idx++);
      }
      return BuiltIn.PyExcStopIteration;
    }

    @Override
    public PyObject get(int idx) throws PyException {
      if (idx >= size()) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.KeyError, "index " + idx + " out of bound");
        return null;
      }
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
