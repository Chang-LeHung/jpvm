package org.jpvm.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PyListObject extends PyObject {

   private final List<PyObject> obItem;

   public PyListObject() {
      this(1);
   }

   public PyListObject(int size) {
      obItem = new ArrayList<>(size);
   }

   public void app1(PyObject obj) {
      obItem.add(obj);
   }

   public void insert(int idx, PyObject obj) {
      obItem.add(idx, obj);
   }

   public boolean pop() {
      if (obItem.size() == 0)
         throw new IndexOutOfBoundsException("pop() PyListObject has no elements");
     return obItem.remove(obItem.size() - 1) != null;
   }

   public boolean remove(PyObject obj) {
      return obItem.remove(obj);
   }

   public void sort(boolean reverse) {
      if (reverse)
         Collections.reverse(obItem);
   }

   public PyObject get(int idx) {
      if (idx >= obItem.size())
         throw new IndexOutOfBoundsException();
      return obItem.get(idx);
   }

   public int size(){
      return obItem.size();
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("[");
      for (PyObject object : obItem) {
         builder.append(object.toString());
         builder.append(", ");
      }
      if (builder.length() > 2)
         builder.delete(builder.length() - 2, builder.length());
      builder.append("]");
      return builder.toString();
   }
}
