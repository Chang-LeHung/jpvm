package org.jpvm.excptions;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTraceBackType;

public class PyTraceBackObject extends PyObject {
  public static final PyObject type = new PyTraceBackType(PyTraceBackObject.class);
  private final PyFrameObject frame;
  private final int lineNo;

  private PyTraceBackObject next;

  public PyTraceBackObject(PyFrameObject frame) {
    this.frame = frame;
    int cursor = frame.getLastI();
    lineNo = frame.addressToLine(cursor);
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
    String code = "";
    if (frame.getCode().getCoFileName() != null) {
      Path path = Paths.get(((PyUnicodeObject) frame.getCode().getCoFileName()).getData());
      File file = path.toFile();
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        int n = 0;
        while (n++ < lineNo) {
          code = reader.readLine();
        }
      } catch (IOException ignore) {
      }
    }
    StringBuilder builder = new StringBuilder();
    builder.append("\tFile: \"");
    builder.append(frame.getCode().getCoFileName());
    builder.append("\", ");
    builder.append(" line ");
    builder.append(lineNo);
    builder.append(", in ");
    builder.append(frame.getCode().getCoName());
    builder.append("\n");
    builder.append("\t\t");
    builder.append(code.strip());
    builder.append("\n");
    if (next != null) {
      builder.append(next.str());
    }
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  public PyTraceBackObject getNext() {
    return next;
  }

  public void setNext(PyTraceBackObject next) {
    this.next = next;
  }
}
