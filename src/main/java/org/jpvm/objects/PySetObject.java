package org.jpvm.objects;

import java.util.HashSet;
import java.util.Set;

public class PySetObject extends PyObject{

   private Set<PyObject> set;

   private boolean isFrozen;

   public PySetObject() {
      this.set = new HashSet<>();
   }

   public PySetObject(boolean isFrozen) {
      this.isFrozen = isFrozen;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("{");
      for (PyObject object : set) {
         builder.append(object.toString());
         builder.append(", ");
      }
      if (builder.length() > 2)
         builder.delete(builder.length() - 2, builder.length());
      builder.append("}");
      return builder.toString();
   }

   public boolean isFrozen() {
      return isFrozen;
   }

   public void setFrozen(boolean frozen) {
      isFrozen = frozen;
   }

   public void put(PyObject key) {
      set.add(key);
   }

   public boolean contains(PyObject key) {
      return set.contains(key);
   }
}
