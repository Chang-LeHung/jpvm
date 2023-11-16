package org.jpvm.objects;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.internal.NumberHelper;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.objects.types.PyUnicodeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

/** Use {@linkplain StandardCharsets#UTF_8} as default charset */
public class PyUnicodeObject extends PyObject
    implements PyNumberMethods, PySequenceMethods, PyMappingMethods, TypeIterable {

  public static final PyTypeType type = PyUnicodeType.getInstance();

  public static Map<String, PyUnicodeObject> internStr = new HashMap<>();
  private final String s;
  private byte[] data;

  public PyUnicodeObject(byte[] data) {
    this.data = data;
    s = new String(data, StandardCharsets.UTF_8);
  }

  public PyUnicodeObject(String data) {
    s = data;
    this.data = data.getBytes(StandardCharsets.UTF_8);
  }

  public static PyBoolObject check(PyObject o) {
    if (o == type) return BuiltIn.True;
    return BuiltIn.False;
  }

  public static PyUnicodeObject getOrCreateFromInternStringPool(String s, boolean intern) {
    if (internStr == null) return new PyUnicodeObject(s);
    if (intern) {
      if (internStr.containsKey(s)) return internStr.get(s);
      else {
        PyUnicodeObject object = new PyUnicodeObject(s);
        internStr.put(s, object);
        return object;
      }
    } else {
      return internStr.getOrDefault(s, new PyUnicodeObject(s));
    }
  }

  @PyClassMethod
  public PyObject upper(PyTupleObject args, PyDictObject kwArgs) {
    return new PyUnicodeObject(s.toUpperCase());
  }

  @PyClassMethod
  public PyObject lower(PyTupleObject args, PyDictObject kwArgs) {
    return new PyUnicodeObject(s.toLowerCase());
  }

  @PyClassMethod
  public PyObject startswith(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o) {
        if (s.startsWith(o.s)) return BuiltIn.True;
        return BuiltIn.False;
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "str method startswith require one str argument");
    return null;
  }

  @PyClassMethod
  public PyObject endswith(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o) {
        if (s.endsWith(o.s)) return BuiltIn.True;
        return BuiltIn.False;
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "str method endswith require one str argument");
    return null;
  }

  @PyClassMethod
  public PyObject index(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o) {
        int i = s.indexOf(o.s);
        return PyLongObject.getLongObject(i);
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method index require one str argument");
    return null;
  }

  @PyClassMethod
  public PyObject count(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o) {
        int i = countSubstringOccurrences(s, o.getData());
        return PyLongObject.getLongObject(i);
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method cout require one str argument");
    return null;
  }

  public int countSubstringOccurrences(String str, String substring) {
    int count = 0;
    int index = str.indexOf(substring);
    while (index != -1) {
      count++;
      index = str.indexOf(substring, index + substring.length());
    }
    return count;
  }

  @PyClassMethod
  public PyObject split(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o) {
        PyListObject list = new PyListObject();
        for (String str : s.split(o.s)) {
          list.add(new PyUnicodeObject(str));
        }
        return list;
      }
    } else {
      PyListObject list = new PyListObject();
      for (String str : s.split("[, ]")) {
        list.add(new PyUnicodeObject(str));
      }
      return list;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method split error");
    return null;
  }

  @PyClassMethod
  public PyObject strip(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      return new PyUnicodeObject(s.strip());
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method strip require one str argument");
    return null;
  }

  @PyClassMethod
  public PyObject replace(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o1 && args.get(1) instanceof PyUnicodeObject o2) {
        return new PyUnicodeObject(s.replace(o1.s, o2.s));
      }
    }
    PyErrorUtils.pyErrorFormat(
        PyErrorUtils.TypeError, "str method replace require two str argument");
    return null;
  }

  @PyClassMethod
  public PyObject join(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      StringBuilder builder = new StringBuilder();
      if (object instanceof PyListObject o) {
        for (int i = 0; i < o.size() - 1; i++) {
          if (o.get(i) instanceof PyUnicodeObject str) {
            builder.append(str.getData());
            builder.append(s);
          } else {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, o.repr() + " is not a str object");
            return null;
          }
        }
        if (o.get(o.size() - 1) instanceof PyUnicodeObject str) {
          builder.append(str.getData());
        } else {
          PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, o.repr() + " is not a str object");
          return null;
        }
        return new PyUnicodeObject(builder.toString());
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method join require two str argument");
    return null;
  }

  @PyClassMethod
  public PyObject isnumeric(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      for (int i = 0; i < s.length(); i++) {
        if (!Character.isDigit(s.charAt(i))) return BuiltIn.False;
      }
      return BuiltIn.True;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method strip require one str argument");
    return null;
  }

  @PyClassMethod
  public PyObject isalpha(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      for (int i = 0; i < s.length(); i++) {
        if (!Character.isAlphabetic(s.charAt(i))) return BuiltIn.False;
      }
      return BuiltIn.True;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str method strip require one str argument");
    return null;
  }

  public String getData() {
    return new String(data, StandardCharsets.UTF_8);
  }

  public void setData(String s) {
    this.data = s.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String toString() {
    return s;
  }

  @Override
  public Object toJavaType() {
    return new String(data, StandardCharsets.UTF_8);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PyUnicodeObject that)) return false;
    return Arrays.equals(data, that.data);
  }

  @Override
  public PyLongObject hash() {
    int h = 0;
    for (byte v : data) {
      h = 31 * h + (v & 0xff);
    }
    return new PyLongObject(h);
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return super.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return this;
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject("'" + s + "'");
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    switch (op) {
      case Py_EQ -> {
        if (!(o instanceof PyUnicodeObject d)) return BuiltIn.False;
        if (new String(data, StandardCharsets.UTF_8).equals(d.toJavaType())) return BuiltIn.True;
        return BuiltIn.False;
      }
      case Py_LT -> {
        if (!(o instanceof PyUnicodeObject d)) {
          return BuiltIn.False;
        } else {
          int llen = this.s.length();
          int rlen = d.s.length();
          int i = 0, j = 0;
          for (; i < llen && j < rlen; i++, j++) {
            if (this.s.charAt(i) == d.s.charAt(j)) {
            } else if (this.s.charAt(i) < d.s.charAt(j)) {
              return BuiltIn.True;
            } else {
              return BuiltIn.False;
            }
          }
          if (llen < rlen) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
      }
      case Py_GT -> {
        if (!(o instanceof PyUnicodeObject d)) {
          return BuiltIn.False;
        } else {
          int llen = this.s.length();
          int rlen = d.s.length();
          int i = 0, j = 0;
          for (; i < llen && j < rlen; i++, j++) {
            if (this.s.charAt(i) == d.s.charAt(j)) {
            } else if (this.s.charAt(i) > d.s.charAt(j)) {
              return BuiltIn.True;
            } else {
              return BuiltIn.False;
            }
          }
          if (llen > rlen) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
      }
      case Py_LE -> {
        if (!(o instanceof PyUnicodeObject d)) {
          return BuiltIn.False;
        } else {
          int llen = this.s.length();
          int rlen = d.s.length();
          int i = 0, j = 0;
          for (; i < llen && j < rlen; i++, j++) {
            if (this.s.charAt(i) <= d.s.charAt(j)) {
              return BuiltIn.True;
            } else {
              return BuiltIn.False;
            }
          }
          if (llen < rlen) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
      }
      case Py_GE -> {
        if (!(o instanceof PyUnicodeObject d)) {
          return BuiltIn.False;
        } else {
          int llen = this.s.length();
          int rlen = d.s.length();
          int i = 0, j = 0;
          for (; i < llen && j < rlen; i++, j++) {
            if (this.s.charAt(i) >= d.s.charAt(j)) {
              return BuiltIn.True;
            } else {
              return BuiltIn.False;
            }
          }
          if (llen > rlen) {
            return BuiltIn.True;
          } else {
            return BuiltIn.False;
          }
        }
      }
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "not support operator " + op);
    return null;
  }

  @Override
  public PyBoolObject isHashable() {
    return BuiltIn.True;
  }

  @Override
  public PyObject mpLength(PyObject o) {
    return new PyLongObject(s.length());
  }

  @Override
  public PyObject mpSubscript(PyObject o) throws PyException {
    if (o instanceof PySliceObject slice) {
      PyListObject list = slice.unpacked(this);
      assert list != null;
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < list.size(); ++i) {
        int idx = (int) ((PyLongObject) list.get(i)).getData();
        builder.append(s.charAt(idx));
      }
      return new PyUnicodeObject(builder.toString());
    } else {
      Long n = NumberHelper.transformPyObject2Long(o);
      if (n == null) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "require PyNumberMethods type");
        return null;
      }
      return new PyUnicodeObject(s.substring(n.intValue(), n.intValue() + 1));
    }
  }

  @Override
  public PyObject mod(PyObject o) throws PyException {
    if (o instanceof PyTupleObject tuple) {
      Object[] objects = new Object[tuple.size()];
      for (int i = 0; i < tuple.size(); i++) {
        PyObject arg = tuple.get(i);
        if (arg instanceof PyLongObject l) {
          objects[i] = l.getData();
        } else if (arg instanceof PyUnicodeObject s) {
          objects[i] = s.getData();
        } else if (arg instanceof PyFloatObject f) {
          objects[i] = f.getData();
        } else {
          objects[i] = arg.repr().getData();
        }
      }
      return new PyUnicodeObject(String.format(s, objects));
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "str mod method require a tuple argument");
    return null;
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(s.length());
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyException {
    if (o instanceof PyUnicodeObject u) {
      return new PyUnicodeObject(s + u.getData());
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "sqConcat: parameter o require type str");
    return null;
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyException {
    StringBuilder builder = new StringBuilder();
    Long l = NumberHelper.transformPyObject2Long(o);
    if (l == null) {
      PyErrorUtils.pyErrorFormat(
          PyErrorUtils.TypeError, "sqRepeat: parameter o require type PyNumberMethods");
      return null;
    }
    builder.append(String.valueOf(s).repeat(Math.max(0, l.intValue())));
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyException {
    if (o instanceof PyUnicodeObject u) {
      return s.contains(u.getData()) ? BuiltIn.True : BuiltIn.False;
    }
    PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "sqContain: parameter o require type str");
    return null;
  }

  @Override
  public TypeDoIterate getIterator() throws PyNotImplemented {
    return new PyUnicodeItrObject();
  }

  public static class PyUnicodeItrType extends PyTypeType {
    public PyUnicodeItrType() {
      super(PyUnicodeItrObject.class);
      this.name = "str_iterator";
    }
  }

  public class PyUnicodeItrObject extends PyObject implements TypeDoIterate {

    public static final PyTypeType type = new PyUnicodeItrType();
    private int idx;

    public PyUnicodeItrObject() {
      idx = 0;
    }

    @Override
    public PyObject next() throws PyException {
      if (idx < size()) return new PyUnicodeObject(s.substring(idx, ++idx));
      return PyErrorUtils.pyErrorFormat(PyErrorUtils.StopIteration, "");
    }

    @Override
    public PyObject get(int idx) throws PyException {
      if (idx >= size()) {
        PyErrorUtils.pyErrorFormat(PyErrorUtils.KeyError, idx + " is out of bound for str " + s);
        return null;
      }
      return new PyUnicodeObject(s.substring(idx, idx + 1));
    }

    @Override
    public int size() {
      return s.length();
    }

    @Override
    public boolean hasNext() {
      return idx < size();
    }
  }
}
