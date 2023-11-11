package org.jpvm.excptions.objs;

public class PyParametersError extends PyException {
  public PyParametersError(String message, boolean isInternalError) {
    super(message, isInternalError);
  }
}
