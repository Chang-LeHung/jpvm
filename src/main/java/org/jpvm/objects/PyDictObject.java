package org.jpvm.objects;

import org.jpvm.objects.pyinterface.PyArgs;
import org.jpvm.objects.types.PyBytesType;
import org.jpvm.objects.types.PyDictType;

import java.util.HashMap;
import java.util.Map;

public class PyDictObject extends PyObject implements PyArgs {

   public static PyObject type = new PyDictType();

   private final Map<PyObject, PyObject> map;

   public PyDictObject() {
      this.map = new HashMap<>();
   }

   public PyObject put(PyObject key, PyObject val) {
      return map.put(key, val);
   }

   public PyObject add(PyObject key, PyObject val) {
      return map.put(key, val);
   }

   public PyObject get(PyObject key) {
      return map.get(key);
   }

   public void addAll(PyDictObject dict) {
      map.putAll(dict.getMap());
   }

   public Map<PyObject, PyObject> getMap() {
      return map;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("{");

      map.forEach((x, y) -> {
         builder.append(x.toString());
         builder.append(": ");
         builder.append(y.toString());
         builder.append(", ");
      });
      if (builder.length() > 2)
         builder.delete(builder.length() - 2, builder.length());
      builder.append("}");
      return builder.toString();
   }

   public boolean containKey(PyObject key) {

      return map.containsKey(key);
   }

   @Override
   public Object toJavaType() {
      return map;
   }

   @Override
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
   }
}
