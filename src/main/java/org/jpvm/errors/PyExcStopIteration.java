package org.jpvm.errors;

import org.jpvm.objects.PyUnicodeObject;

public class PyExcStopIteration extends PyException{
   public PyExcStopIteration(String message) {
      super(message);
   }

   @Override
   public PyUnicodeObject log() {
      return new PyUnicodeObject("PyExcStopIteration:" + getMessage());
   }
}
