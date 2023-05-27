package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;
import org.jpvm.objects.types.PyComplexType;

public class PyComplexObject extends PyObject implements PyArgs {

   public static PyObject type = new PyComplexType();
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

   /**
    *
    * @return double[] [0] for image [1] for real
    */
   @Override
   public Object toJavaType() {
      return new double[] {(double)getImage().toJavaType(),
          (double)getReal().toJavaType()};
   }

   @Override
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
   }
}
