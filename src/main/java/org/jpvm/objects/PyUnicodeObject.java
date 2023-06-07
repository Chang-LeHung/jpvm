package org.jpvm.objects;

import org.jpvm.errors.*;
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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Use {@linkplain StandardCharsets#UTF_8} as default charset
 */
public class PyUnicodeObject extends PyObject
    implements PyNumberMethods, PySequenceMethods, PyMappingMethods,
    TypeIterable {

  public static PyObject type = new PyUnicodeType();

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
    return new PyBoolObject(o == type);
  }

  public static PyUnicodeObject getOrCreateFromInternStringPool(String s, boolean intern) {
    if (internStr == null)
      return new PyUnicodeObject(s);
    if (intern) {
      if (internStr.containsKey(s))
        return internStr.get(s);
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
        if (s.startsWith(o.s))
          return BuiltIn.True;
        return BuiltIn.False;
      }
    }
    throw new PyException("str method startswith require one str argument");
  }

  @PyClassMethod
  public PyObject endswith(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o) {
        if (s.endsWith(o.s))
          return BuiltIn.True;
        return BuiltIn.False;
      }
    }
    throw new PyException("str method endswith require one str argument");
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
    throw new PyException("str method index require one str argument");
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
    throw new PyException("str method startswith require one str argument");
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
    }else {
      PyListObject list = new PyListObject();
      for (String str : s.split("[, ]")) {
        list.add(new PyUnicodeObject(str));
      }
      return list;
    }
    throw new PyException("str method split error");
  }

  @PyClassMethod
  public PyObject strip(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      return new PyUnicodeObject(s.strip());
    }
    throw new PyException("str method strip require one str argument");
  }

  @PyClassMethod
  public PyObject replace(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 2) {
      PyObject object = args.get(0);
      if (object instanceof PyUnicodeObject o1 && args.get(1) instanceof PyUnicodeObject o2) {
        return new PyUnicodeObject(s.replace(o1.s, o2.s));
      }
    }
    throw new PyException("str method replace require two str argument");
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
          }else
            throw new PyException(o.repr() + " is not a str object");
        }
        if (o.get(o.size() - 1) instanceof PyUnicodeObject str) {
          builder.append(str.getData());
        }else
          throw new PyException(o.repr() + " is not a str object");
        return new PyUnicodeObject(builder.toString());
      }
    }
    throw new PyException("str method join require two str argument");
  }

  @PyClassMethod
  public PyObject isnumeric(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      for (int i = 0; i < s.length(); i++) {
        if (!Character.isDigit(s.charAt(i)))
          return BuiltIn.False;
      }
      return BuiltIn.True;
    }
    throw new PyException("str method strip require one str argument");
  }

  @PyClassMethod
  public PyObject isalpha(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 0) {
      for (int i = 0; i < s.length(); i++) {
        if (!Character.isAlphabetic(s.charAt(i)))
          return BuiltIn.False;
      }
      return BuiltIn.True;
    }
    throw new PyException("str method strip require one str argument");
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
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (op == Operator.Py_EQ) {
      if (!(o instanceof PyUnicodeObject d))
        return BuiltIn.False;
      if (new String(data, StandardCharsets.UTF_8).equals(d.toJavaType()))
        return BuiltIn.True;
      return BuiltIn.False;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
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
  public PyObject mpSubscript(PyObject o) throws PyTypeNotMatch {
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
      if (n == null)
        throw new PyTypeNotMatch("require PyNumberMethods type");
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
        }else if (arg instanceof PyUnicodeObject s) {
          objects[i] = s.getData();
        }else if (arg instanceof PyFloatObject f) {
          objects[i] = f.getData();
        }else {
          objects[i] = arg.repr().getData();
        }
      }
      return new PyUnicodeObject(String.format(s, objects));
    }
    throw new PyException("str mod method require a tuple argument");
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(s.length());
  }

  @Override
  public PyObject sqConcat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    if (o instanceof PyUnicodeObject u) {
      return new PyUnicodeObject(s + u.getData());
    }
    throw new PyTypeNotMatch("sqConcat: parameter o require type str");
  }

  @Override
  public PyObject sqRepeat(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
    StringBuilder builder = new StringBuilder();
    Long l = NumberHelper.transformPyObject2Long(o);
    if (l == null)
      throw new PyTypeNotMatch("sqRepeat: parameter o require type PyNumberMethods");
    builder.append(String.valueOf(s).repeat(Math.max(0, l.intValue())));
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyTypeNotMatch {
    if (o instanceof PyUnicodeObject u) {
      return new PyBoolObject(s.contains(u.getData()));
    }
    throw new PyTypeNotMatch("sqContain: parameter o require type str");
  }

  @Override
  public TypeDoIterate getIterator() throws PyNotImplemented {
    return new PyUnicodeItrObject();
  }

  public static class PyUnicodeItrType extends PyTypeType {
    public PyUnicodeItrType() {
      this.name = "str_iterator";
    }

  }

  public class PyUnicodeItrObject extends PyObject implements TypeDoIterate {

    public static PyObject type = new PyUnicodeItrType();
    private int idx;

    public PyUnicodeItrObject() {
      idx = 0;
    }

    @Override
    public PyObject next() throws PyException {
      if (idx < size())
        return new PyUnicodeObject(s.substring(idx, ++idx));
      return BuiltIn.PyExcStopIteration;
    }

    @Override
    public PyObject get(int idx) throws PyIndexOutOfBound, PyNotImplemented {
      if (idx >= size())
        throw new PyIndexOutOfBound(idx + " is out of bound for str " + s);
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
