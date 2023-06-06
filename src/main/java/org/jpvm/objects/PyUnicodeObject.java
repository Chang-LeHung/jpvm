package org.jpvm.objects;

import org.jpvm.errors.*;
import org.jpvm.internal.NumberHelper;
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
