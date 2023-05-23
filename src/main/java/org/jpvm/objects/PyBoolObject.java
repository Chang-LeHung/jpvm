package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;

public class PyBoolObject extends PyObject implements PyArgs {

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

   @Override
   public Object toJavaType() {
      return bool;
   }
}
