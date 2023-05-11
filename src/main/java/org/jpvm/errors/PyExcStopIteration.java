package org.jpvm.errors;

public class PyExcStopIteration extends PyException{
   public PyExcStopIteration(String message) {
      super(message);
   }

   @Override
   public void log() {
      System.err.println("PyExcStopIteration:" + getMessage());
   }
}
