package org.jpvm.exceptions.jobjs;

public class PyParametersError extends PyException {
  public PyParametersError(String message, boolean isInternalError) {
    super(message, isInternalError);
  }
}
