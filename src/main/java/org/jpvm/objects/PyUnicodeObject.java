package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;

import java.nio.charset.StandardCharsets;

/**
 * Use {@linkplain StandardCharsets#UTF_8} as default charset
 */
public class PyUnicodeObject extends PyObject implements PyArgs {

   private byte[] data;
   public PyUnicodeObject(byte[] data) {
      this.data = data;
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
}
