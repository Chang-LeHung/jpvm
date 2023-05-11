package org.jpvm.objects;

import java.util.HashMap;
import java.util.Map;

public class PyDictObject extends PyObject{

   private Map<PyObject, PyObject> map;

   public PyDictObject() {
      this.map = new HashMap<>();
   }

   public PyObject put(PyObject key, PyObject val) {
      return map.put(key, val);
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("{");

      map.forEach((x, y) -> {
         builder.append("[");
         builder.append("key=");
         builder.append(x.toString());
         builder.append(", val=");
         builder.append(y.toString());
         builder.append("]");
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
}
