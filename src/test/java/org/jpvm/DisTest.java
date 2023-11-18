package org.jpvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.module.Disassembler;
import org.jpvm.module.Marshal;
import org.jpvm.objects.PyObject;
import org.jpvm.pycParser.PyCodeObject;
import org.junit.Test;

public class DisTest {

  @Test
  public void test() throws IOException, PyException {
    String filename = "src/test/resources/pys/__pycache__/add.cpython-38.pyc";
    FileInputStream stream = new FileInputStream(filename);
    stream.skipNBytes(16);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    System.out.println(pyCodeObject);
    Disassembler disassembler = new Disassembler(pyCodeObject);
//    disassembler.dis();
    stream.close();
  }

  @Test
  public void testPyc() throws IOException, PyException {
    String filename = "src/test/resources/pys/__pycache__/pycparser.cpython-38.pyc";
    FileInputStream stream = new FileInputStream(filename);
    stream.skipNBytes(16);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    System.out.println(pyCodeObject);
    Disassembler disassembler = new Disassembler(pyCodeObject);
//    disassembler.dis();
    stream.close();
  }

  @Test
  public void testLoop() throws IOException, PyException {
    String filename = "src/test/resources/pys/__pycache__/loop.cpython-38.pyc";
    FileInputStream stream = new FileInputStream(filename);
    stream.skipNBytes(16);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    Disassembler disassembler = new Disassembler(pyCodeObject);
//    disassembler.dis();
    stream.close();
  }

  @Test
  public void test01() throws IOException, PyException {
    String filename = "src/test/resources/pys/__pycache__/add.bin";
    FileInputStream stream = new FileInputStream(filename);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    System.out.println(pyCodeObject);
    Disassembler disassembler = new Disassembler(pyCodeObject);
//    disassembler.dis();
    stream.close();
  }

  @Test
  public void test02() throws IOException, PyException {
    String filename = "src/test/resources/pys/__pycache__/pyobjects.bin";
    FileInputStream stream = new FileInputStream(filename);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
    PyObject o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    o = marshal.loadPyObject(buffer);
    System.out.println(o);
    stream.close();
  }

  @Test
  public void testString() throws IOException, PyException {
    String filename = "src/test/resources/syntax/__pycache__/test05.cpython-38.pyc";
    FileInputStream stream = new FileInputStream(filename);
    stream.skipNBytes(16);
    int available = stream.available();
    byte[] bytes = new byte[available];
    int s = stream.read(bytes);
    Marshal marshal = new Marshal();
    PyCodeObject pyCodeObject = marshal.loadCodeObject(bytes);
    Disassembler disassembler = new Disassembler(pyCodeObject);
//    disassembler.dis();
    stream.close();
  }
}
