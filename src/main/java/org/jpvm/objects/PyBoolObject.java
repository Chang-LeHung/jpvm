package org.jpvm.objects;

public class PyBoolObject extends PyObject{

   private boolean bool;

   public PyBoolObject(boolean bool) {
      this.bool = bool;
   }

   public boolean isBool() {
      return bool;
   }

   public void setBool(boolean bool) {
      this.bool = bool;
   }

   @Override
   public String toString() {
      return "PyBoolObject{" +
          "bool=" + bool +
          '}';
   }
}
