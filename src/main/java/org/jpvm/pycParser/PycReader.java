package org.jpvm.pycParser;

import java.io.FileInputStream;
import java.io.IOException;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.module.Marshal;

/**
 * .pyc file structure +----------------+ | Magic Number | 4 bytes | Bit Field | 4 bytes | Modified
 * Date | 4 bytes | File Size | 4 bytes | Code Object | +----------------+
 */
public class PycReader {

  private final String pyc;
  private int timestamp;
  private int magicNumber;
  private int bitFiled;
  private int mappingPyFileSize;
  private PyCodeObject pyCodeObject;

  public PycReader(String filename) {
    pyc = filename;
  }

  public void doParse() throws IOException, PyException {
    var stream = new FileInputStream(pyc);
    magicNumber = BinaryUtil.nextInt(stream);
    bitFiled = BinaryUtil.nextInt(stream);
    timestamp = BinaryUtil.nextInt(stream);
    mappingPyFileSize = BinaryUtil.nextInt(stream);
    Marshal marshal = new Marshal();
    pyCodeObject = (PyCodeObject) marshal.loadPyObject(stream);
    // release resources
    stream.close();
  }

  public PyCodeObject getCodeObject() {
    return pyCodeObject;
  }

  public int getMappingPyFileSize() {
    return mappingPyFileSize;
  }

  public int getTimestamp() {
    return timestamp;
  }

  public int getMagicNumber() {
    return magicNumber;
  }

  public int getBitFiled() {
    return bitFiled;
  }
}
