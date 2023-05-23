package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;

public class PyLongObject extends PyObject implements PyArgs {
   private long data;

   public PyLongObject(long data) {
      this.data = data;
   }

   @Override
   public String toString() {
      return String.valueOf(data);
   }

   public PyLongObject(int data) {
      this.data = data;
   }

   public long getData() {
      return data;
   }

   public void setData(long data) {
      this.data = data;
   }

   public void fromString(String s) {
      data = Integer.parseInt(s);
   }

   @Override
   public Object toJavaType() {
      return data;
   }
}
