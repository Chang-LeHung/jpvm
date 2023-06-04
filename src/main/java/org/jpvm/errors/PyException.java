package org.jpvm.errors;

import org.jpvm.objects.PyUnicodeObject;

public class PyException extends Exception implements PyExcLogging {

  private boolean isInternalError;

  private final String message;

  public PyException(String message) {
    this.message = message;
  }

  public PyException(String message, boolean isInternalError) {
    super(message);
    this.isInternalError = isInternalError;
    this.message = message;
  }

  @Override
  public PyUnicodeObject log() {
    return new PyUnicodeObject(message);
  }

  public String getMessage() {
    return message;
  }

  public boolean isInternalError() {
    return isInternalError;
  }

  public void setInternalError(boolean internalError) {
    isInternalError = internalError;
  }
}
