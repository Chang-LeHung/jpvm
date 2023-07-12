package org.jpvm.objects;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.objects.types.PyTraceBackType;

import java.util.ArrayList;
import java.util.List;

public class PyTraceBackObject extends PyObject {
  public static PyObject type = new PyTraceBackType(PyTraceBackObject.class);

  private final List<PyFrameObject> frames;

  private final PyPythonException exception;

  public PyTraceBackObject(PyPythonException exception) {
    this.exception = exception;
    frames = new ArrayList<>();
  }

  public void add(PyFrameObject frame) {
    frames.add(frame);
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    StringBuilder builder = new StringBuilder();
    builder.append("Traceback (most recent call last):\n");
    for (PyFrameObject frame : frames) {
      builder.append("\tFile: \"");
      builder.append(frame.getCode().getCoFileName());
      builder.append("\", ");
      builder.append(frame.getCode().getCoName());
      builder.append("line ");
      builder.append(frame.addressToLine());
      builder.append(", in ");
      builder.append(frame.getCode().getCoName());
      builder.append("\n");
    }
    builder.append(exception.str());
    builder.append("\n");
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }
}
