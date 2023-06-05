package org.jpvm.errors;

public class PyParametersError extends PyException {
  public PyParametersError(String message, boolean isInternalError) {
    super(message, isInternalError);
  }
}
