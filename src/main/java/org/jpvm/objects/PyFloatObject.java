package org.jpvm.objects;

public class PyFloatObject extends PyObject{

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
}
