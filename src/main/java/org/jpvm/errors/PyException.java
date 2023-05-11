package org.jpvm.errors;

import org.jpvm.objects.PyObject;

public class PyException extends PyObject implements PyExcLogging {

   private String message;

   public PyException(String message) {
      this.message = message;
   }

   @Override
   public void log() {
      System.err.println(message);
   }

   public String getMessage() {
      return message;
   }
}
