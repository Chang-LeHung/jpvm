package org.jpvm.objects;

import java.util.HashMap;

public class PyObject {

   private HashMap<String, PyObject> __dict__;

   @Override
   public String toString() {
      return "<PyObject>";
   }
}
