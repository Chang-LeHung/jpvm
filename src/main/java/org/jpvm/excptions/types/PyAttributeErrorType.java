package org.jpvm.excptions.types;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.types.PyTypeType;

public class PyAttributeErrorType extends PyTypeType {

  public PyAttributeErrorType() {
    super(PyPythonException.class);
    name = "AttributeError";
    addBase(PyPythonException.type);
  }

  @Override
  public String toString() {
    return "AttributeError";
  }
}
