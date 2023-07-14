package org.jpvm.excptions;

import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.types.PyTraceBackType;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class PyTraceBackObject extends PyObject {
  public static PyObject type = new PyTraceBackType(PyTraceBackObject.class);
  private final PyFrameObject frame;
  private final int lineNo;

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
    if (frame.getCode().getParentDir() != null) {
      Path path = Paths.get(frame.getCode().getParentDir());
      String coFileName = ((PyUnicodeObject) frame.getCode().getCoFileName()).getData();
      String[] names = coFileName.split("/");
      Path filePath = path.resolve(names[names.length - 1]);
      File file = filePath.toFile();
      try {
        Scanner scanner = new Scanner(file);
        int n = 0;
        while (n++ < lineNo) {
          code = scanner.nextLine();
        }
        scanner.close();
      } catch (IOException ignore) {
      }
    }
    StringBuilder builder = new StringBuilder();
    builder.append("Traceback (most recent call last):\n");
    builder.append("\tFile: \"");
    builder.append(frame.getCode().getCoFileName());
    builder.append("\", ");
    builder.append(" line ");
    builder.append(lineNo);
    builder.append(", in ");
    builder.append(frame.getCode().getCoName());
    builder.append("\n");
    builder.append("\t\t");
    builder.append(code);
    builder.append("\n");
    return new PyUnicodeObject(builder.toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }
}
