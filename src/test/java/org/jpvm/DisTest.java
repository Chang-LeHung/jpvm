package org.jpvm;

import org.jpvm.module.Disassembler;
import org.jpvm.module.Marshal;
import org.jpvm.objects.PyObject;
import org.jpvm.pycParser.CodeObject;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DisTest {

   @Test
   public void test() throws IOException {
      String filename = "src/test/resources/pys/__pycache__/add.cpython-38.pyc";
      FileInputStream stream = new FileInputStream(filename);
      stream.skipNBytes(16);
      int available = stream.available();
      byte[] bytes = new byte[available];
      int s = stream.read(bytes);
      Marshal marshal = new Marshal();
      CodeObject codeObject = marshal.loadCodeObject(bytes);
      Disassembler disassembler = new Disassembler(codeObject);
      disassembler.dis();
      stream.close();
   }

   @Test
   public void testPyc() throws IOException {
      String filename = "src/test/resources/pys/__pycache__/pycparser.cpython-38.pyc";
      FileInputStream stream = new FileInputStream(filename);
      stream.skipNBytes(16);
      int available = stream.available();
      byte[] bytes = new byte[available];
      int s = stream.read(bytes);
      Marshal marshal = new Marshal();
      CodeObject codeObject = marshal.loadCodeObject(bytes);
      Disassembler disassembler = new Disassembler(codeObject);
      disassembler.dis();
      stream.close();
   }

   @Test
   public void testLoop() throws IOException {
      String filename = "src/test/resources/pys/__pycache__/loop.cpython-38.pyc";
      FileInputStream stream = new FileInputStream(filename);
      stream.skipNBytes(16);
      int available = stream.available();
      byte[] bytes = new byte[available];
      int s = stream.read(bytes);
      Marshal marshal = new Marshal();
      CodeObject codeObject = marshal.loadCodeObject(bytes);
      Disassembler disassembler = new Disassembler(codeObject);
      disassembler.dis();
      stream.close();
   }

   @Test
   public void test01() throws IOException {
      String filename = "src/test/resources/pys/__pycache__/add.bin";
      FileInputStream stream = new FileInputStream(filename);
      int available = stream.available();
      byte[] bytes = new byte[available];
      int s = stream.read(bytes);
      Marshal marshal = new Marshal();
      CodeObject codeObject = marshal.loadCodeObject(bytes);
      System.out.println(codeObject);
      Disassembler disassembler = new Disassembler(codeObject);
      disassembler.dis();
      stream.close();
   }

   @Test
   public void test02() throws IOException {
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
}
