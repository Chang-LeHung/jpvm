package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;
import org.jpvm.objects.types.PyUnicodeType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Use {@linkplain StandardCharsets#UTF_8} as default charset
 */
public class PyUnicodeObject extends PyObject implements PyArgs {

   public static PyObject type = new PyUnicodeType();

   private byte[] data;
   public PyUnicodeObject(byte[] data) {
      this.data = data;
   }

   public PyUnicodeObject(String data) {
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
      return "'" + getData() + "'";
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
   public int hashCode() {
      return Arrays.hashCode(data);
   }

   @Override
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
   }
}
