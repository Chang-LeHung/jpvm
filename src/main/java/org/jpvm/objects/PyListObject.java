package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeName;
import org.jpvm.objects.types.PyListType;
import org.jpvm.python.BuiltIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PyListObject extends PyObject
                     implements PyArgs, TypeIterable {

   public static PyObject type = new PyListType();

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

   public void append(PyObject obj) {
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

   public void set(int idx, PyObject o) {
      obItem.set(idx, o);
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

   @Override
   public Object toJavaType() {
      return obItem;
   }

   @Override
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
   }

   @Override
   public PyObject getIterator() {
      return new PyListItrObject();
   }

   public static class PyListItrType extends PyObject implements TypeName {
      private final PyUnicodeObject name;

      public PyListItrType(){
         name = new PyUnicodeObject("list_iterator");
      }
      @Override
      public PyUnicodeObject getTypeName() {
         return name;
      }
   }

   public class PyListItrObject extends PyObject implements TypeDoIterate {

      public static PyObject type = new PyListItrType();
      private int idx;

      public PyListItrObject() {
         idx = 0;
      }

      @Override
      public PyObject next() {
         if (idx < obItem.size()){
            return obItem.get(idx++);
         }
         return BuiltIn.PyExcStopIteration;
      }
   }
}
