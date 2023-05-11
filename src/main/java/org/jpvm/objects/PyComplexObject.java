package org.jpvm.objects;

public class PyComplexObject extends PyObject{

   private PyFloatObject real;
   private PyFloatObject image;

   public PyComplexObject() {}

   public PyComplexObject(PyFloatObject real, PyFloatObject image) {
      this.real = real;
      this.image = image;
   }

   public PyFloatObject getReal() {
      return real;
   }

   public void setReal(PyFloatObject real) {
      this.real = real;
   }

   public PyFloatObject getImage() {
      return image;
   }

   @Override
   public String toString() {
      return real + "+" + image + "i";
   }

   public void setImage(PyFloatObject image) {
      this.image = image;
   }
}
