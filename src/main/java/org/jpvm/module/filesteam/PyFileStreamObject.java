package org.jpvm.module.filesteam;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.PyBytesObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.python.BuiltIn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PyFileStreamObject extends PyObject {

  public static PyObject type = new PyFileStreamType();

  private final RandomAccessFile file;

  private final String encoding;
  private final String filename;

  public PyFileStreamObject(String filename, String args, String encoding) throws FileNotFoundException {
    this.filename = filename;
    this.file = new RandomAccessFile(filename, args);
    this.encoding = encoding;
  }

  public void writeBytes(byte[] bytes) throws IOException {
    file.write(bytes);
  }

  public void writeString(String s) throws IOException {
    file.write(s.getBytes(encoding));
  }

  public void writeLines(TypeIterable o) throws PyNotImplemented {
    PyObject iterator = o.getIterator();
    if (iterator instanceof TypeDoIterate itr) {
      try {
        for (; ; ) {
          PyObject next = itr.next();
          if (next == BuiltIn.PyExcStopIteration) {
            break;
          }
          if (next instanceof PyUnicodeObject uni) {
            String data = uni.getData();
            writeString(data);
          } else if (next instanceof PyBytesObject bytes) {
            byte[] data = bytes.getData();
            writeBytes(data);
          } else {
            throw new PyTypeNotMatch("require type bytes or str");
          }
        }
      } catch (PyException | IOException ignored) {

      }
    }
  }

  @Override
  public String toString() {
    return "<file> " + filename;
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
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(toString());
  }
}
