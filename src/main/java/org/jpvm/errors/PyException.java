package org.jpvm.errors;

import org.jpvm.objects.PyUnicodeObject;

public class PyException extends Exception implements PyExcLogging {

  private final String message;

  public PyException(String message) {
    this.message = message;
  }

  @Override
  public PyUnicodeObject log() {
    return new PyUnicodeObject(message);
  }

  public String getMessage() {
    return message;
  }
}
