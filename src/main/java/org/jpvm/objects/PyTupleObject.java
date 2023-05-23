package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;

import java.util.Arrays;

/**
 * python 当中的元祖对象都是具有固定大小的数组
 */
public class PyTupleObject extends PyObject implements PyArgs {

   private final PyObject[] obItem;


   public PyTupleObject(int size) {
      obItem = new PyObject[size];
   }

   public void set(int idx, PyObject obj) {
      obItem[idx] = obj;
   }

   public PyObject get(int idx) {
      if (idx >= obItem.length)
         throw new IndexOutOfBoundsException("idx = " + idx + " out of PyTupleObject bound with size = " + obItem.length);
      return obItem[idx];
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("(");
      for (PyObject object : obItem) {
         builder.append(object.toString());
         builder.append(", ");
      }
      if (builder.length() > 2)
         builder.delete(builder.length() - 2, builder.length());
      builder.append(")");
      return builder.toString();
   }

   @Override
   public Object toJavaType() {
      return obItem;
   }
}
