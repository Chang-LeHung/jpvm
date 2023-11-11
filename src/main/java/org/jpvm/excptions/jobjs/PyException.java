package org.jpvm.excptions.jobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyException extends Exception implements PyExcLogging {

  private final String message;
  private boolean isInternalError;

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
