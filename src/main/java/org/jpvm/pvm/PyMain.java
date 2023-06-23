package org.jpvm.pvm;

import java.io.IOException;
import java.util.Date;
import org.jpvm.errors.PyException;
import org.jpvm.pycParser.PycReader;

/**
 * jpvm boot from this class
 */
public class PyMain {

  public static void printMetaInfo(PycReader reader) {
    int magicNumber = reader.getMagicNumber();
    int bitFiled = reader.getBitFiled();
    int timestamp = reader.getTimestamp();
    System.out.println("magic number = 0x" + Integer.toHexString(magicNumber));
    System.out.println("bit filed = " + bitFiled);
    System.out.println("date = " + new Date((long) timestamp * 1000));
    System.out.println("CodeObject = " + reader.getCodeObject());
  }

  public static void main(String[] args) throws IOException, PyException {
    String filename = "src/test/resources/pys/__pycache__/add.cpython-38.pyc";
    PycReader reader = new PycReader(filename);
    reader.doParse();
    printMetaInfo(reader);
  }
}
