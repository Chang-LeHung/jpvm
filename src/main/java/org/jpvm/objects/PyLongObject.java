package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;
import org.jpvm.objects.types.PyLongType;
import org.jpvm.protocols.PyNumberMethods;

public class PyLongObject extends PyObject
            implements PyArgs, PyNumberMethods {

   public static PyObject type = new PyLongType();

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

   @Override
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
   }

}
