package org.jpvm.pvm;

import org.jpvm.pycParser.PycReader;

import java.io.IOException;

/**
 * jpvm boot from this class
 */
public class Main {

  public static void printMetaInfo(PycReader reader) {
    int magicNumber = reader.getMagicNumber();
    System.out.println("magic number = ");
  }
  public static void main(String[] args) throws IOException {
    String filename = "";
    PycReader reader = new PycReader(filename);
    reader.doParse();
  }
}
