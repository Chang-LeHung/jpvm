package org.jpvm.excptions.jobjs;

public class PyParametersError extends PyException {
  public PyParametersError(String message, boolean isInternalError) {
    super(message, isInternalError);
  }
}
