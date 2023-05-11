package org.jpvm.bytecode;

import org.jpvm.objects.PyBytesObject;
import org.jpvm.pycParser.CodeObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ByteCodeBuffer {

  private final CodeObject codeObject;
  private final PyBytesObject code;
  private final byte[] codeBuf;

  public ByteCodeBuffer(CodeObject codeObject) {
    this.codeObject = codeObject;
    code = (PyBytesObject) codeObject.getCoCode();
    codeBuf = code.getData();
  }


  public Iterator<Instruction> iterator() {
    return new Itr();
  }

  private class Itr implements Iterator<Instruction> {

    public Itr(int cursor) {
      this.cursor = cursor;
    }

    public Itr() {
      cursor = 0;
    }

    int cursor;

    @Override
    public boolean hasNext() {
      return cursor != codeBuf.length;
    }

    @Override
    public Instruction next() {
      Instruction instruction = new Instruction();
      int opcode;
      int oparg;
      int extendedArg = 0;
      do {
        opcode = codeBuf[cursor++] & 0xff;
        oparg = (codeBuf[cursor++] & 0xff) | extendedArg;
        if (opcode == OpMap.EXTENDED_ARG) {
          extendedArg = opcode << 8;
        } else
          extendedArg = 0;
      } while (opcode != OpMap.EXTENDED_ARG);
      instruction.setOpcode(opcode);
      instruction.setOpname(OpMap.instructions.get(opcode));
      instruction.setOparg(oparg);
      return instruction;
    }

    public boolean resetCursor(int pos) {
      cursor = pos;
      return true;
    }
  }

}
