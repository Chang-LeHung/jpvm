package org.jpvm.module.filestream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyNotImplemented;
import org.jpvm.exceptions.jobjs.PyTypeNotMatch;
import org.jpvm.objects.PyBytesObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.vm.InterpreterState;
import org.jpvm.vm.JPVM;

public class PyFileStreamObject extends PyObject {

  public static final PyTypeType type = new PyFileStreamType();

  private RandomAccessFile file;
  private String encoding = "utf-8";
  private String filename;

  private boolean stdout;
  private boolean stdin;
  private boolean stderr;

  public PyFileStreamObject(String filename, String args, String encoding)
      throws FileNotFoundException {
    if (filename != null) {
      this.filename = filename;
      this.file = new RandomAccessFile(filename, args);
      this.encoding = encoding;
    }
  }

  public PyFileStreamObject() {}

  public PyFileStreamObject(boolean stdout, boolean stdin, boolean stderr) {
    this.stdout = stdout;
    this.stdin = stdin;
    this.stderr = stderr;
  }

  public String getEncoding() {
    return encoding;
  }

  public String getFilename() {
    return filename;
  }

  public boolean isStdout() {
    return stdout;
  }

  public void setStdout(boolean stdout) {
    this.stdout = stdout;
  }

  public boolean isStdin() {
    return stdin;
  }

  public void setStdin(boolean stdin) {
    this.stdin = stdin;
  }

  public boolean isStderr() {
    return stderr;
  }

  public void setStderr(boolean stderr) {
    this.stderr = stderr;
  }

  public void writeBytes(byte[] bytes) throws IOException {
    InterpreterState is = JPVM.getThreadState().getIs();

    if (file != null) {
      file.write(bytes);
    } else if (stdout) {
      System.out.write(bytes);
    } else {
      System.err.write(bytes);
    }
  }

  public void writeString(String s) throws IOException {
    writeBytes(s.getBytes(encoding));
  }

  public void writeLines(TypeIterable o) throws PyException {
    TypeDoIterate itr = o.getIterator();
    try {
      for (; ; ) {
        PyObject next;
        try {
          next = itr.next();
        } catch (PyException ignore) {
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

  public void flush() {
    InterpreterState is = JPVM.getThreadState().getIs();
    if (stdout) {
      System.out.flush();
    } else {
      System.err.flush();
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
