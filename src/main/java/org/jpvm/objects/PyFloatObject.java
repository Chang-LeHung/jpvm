package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;
import org.jpvm.objects.types.PyFloatType;

public class PyFloatObject extends PyObject implements PyArgs {

   public static PyObject type = new PyFloatType();

   private double data;
   public PyFloatObject(float data) {
      this.data = data;
   }

   public PyFloatObject(double data) {
      this.data = data;
   }

   public double getData() {
      return data;
   }


   public void setData(double data) {
      this.data = data;
   }

   public void setData(float data) {
      this.data = data;
   }

   @Override
   public String toString() {
      return String.valueOf(data);
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
