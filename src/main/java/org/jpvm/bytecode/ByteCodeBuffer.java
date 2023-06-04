package org.jpvm.bytecode;

import org.jpvm.objects.PyBytesObject;
import org.jpvm.pycParser.PyCodeObject;

import java.util.Iterator;

public class ByteCodeBuffer implements Iterable<Instruction> {

  private final byte[] codeBuf;

  private Itr itr;

  public ByteCodeBuffer(PyCodeObject pyCodeObject) {
    PyBytesObject code = (PyBytesObject) pyCodeObject.getCoCode();
    codeBuf = code.getData();
  }


  public Iterator<Instruction> iterator() {
    if (itr == null)
      itr = new Itr();
    return itr;
  }

  public void reset(int pos) {
    itr.resetCursor(pos);
  }

  public void increase(int delta) {
    itr.increase(delta);
  }

  private class Itr implements Iterator<Instruction> {

    int cursor;

    public Itr(int cursor) {
      this.cursor = cursor;
    }

    public Itr() {
      cursor = 0;
    }

    @Override
    public boolean hasNext() {
      return cursor < codeBuf.length;
    }

    @Override
    public Instruction next() {
      if (!hasNext())
        throw new UnsupportedOperationException("No more elements");
      Instruction instruction = new Instruction();
      int opcode;
      int oparg = 0; // means no argument
      instruction.setPos(cursor);
      int extendedArg = 0;
      do {
        opcode = codeBuf[cursor++] & 0xff;
        if (opcode == 0)
          break;
        if (opcode >= OpMap.HAVE_ARGUMENT) {
          oparg = (codeBuf[cursor++] & 0xff) | extendedArg;
          if (opcode == OpMap.EXTENDED_ARG) {
            extendedArg = oparg << 8;
          } else
            extendedArg = 0;
        }else cursor++;
      } while (opcode == OpMap.EXTENDED_ARG);
      instruction.setOpcode(opcode);
      instruction.setOpname(OpMap.instructions.get(opcode));
      instruction.setOparg(oparg);
      return instruction;
    }

    public void resetCursor(int pos) {
      cursor = pos;
    }

    public void increase(int delta) {
      cursor += delta;
    }
  }

}
