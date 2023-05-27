package org.jpvm.objects;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.internal.NumberHelper;
import org.jpvm.objects.types.PyUnicodeType;
import org.jpvm.protocols.PyMappingMethods;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Use {@linkplain StandardCharsets#UTF_8} as default charset
 */
public class PyUnicodeObject extends PyObject
    implements PyNumberMethods, PySequenceMethods, PyMappingMethods {

   public static PyObject type = new PyUnicodeType();

   private byte[] data;

   private final String s;
   public PyUnicodeObject(byte[] data) {
      this.data = data;
      s = new String(data, StandardCharsets.UTF_8);
   }

   public PyUnicodeObject(String data) {
      s = data;
      this.data = data.getBytes(StandardCharsets.UTF_8);
   }

   public String getData() {
      return new String(data, StandardCharsets.UTF_8);
   }

   public void setData(String s) {
      this.data = s.getBytes(StandardCharsets.UTF_8);
   }

   @Override
   public String toString() {
      return "'" + s + "'";
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
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
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
   public PyBoolObject richCompare(PyObject o) {
      if (!(o instanceof PyUnicodeObject d))
         return BuiltIn.False;
      if (new String(data, StandardCharsets.UTF_8).equals(d.toJavaType()))
         return BuiltIn.True;
      return BuiltIn.False;
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
            int idx = (int) ((PyLongObject)list.get(i)).getData();
            builder.append(s.charAt(idx));
         }
         return new PyUnicodeObject(builder.toString());
      }else {
         Long n = NumberHelper.transformPyObject2Long(o);
         if (n == null)
            throw new PyTypeNotMatch("require PyNumberMethods type");
         return new PyUnicodeObject(s.substring(n.intValue(), n.intValue()+1));
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
         return new PyUnicodeObject(s + u.getData());
      }
      throw new PyTypeNotMatch("sqContain: parameter o require type str");
   }

}
