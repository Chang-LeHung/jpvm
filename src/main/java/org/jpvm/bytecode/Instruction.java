package org.jpvm.bytecode;

public class Instruction {

  public static int sizeofByteCode = 2;

  private int pos;
  private OpMap.OpName opname;
  private int opcode;
  private int oparg;

  public Instruction() {}

  public Instruction(OpMap.OpName opname, int opcode, int oparg) {
    this.opname = opname;
    this.opcode = opcode;
    this.oparg = oparg;
  }

  public int getPos() {
    return pos;
  }

  public void setPos(int pos) {
    this.pos = pos;
  }

  public OpMap.OpName getOpname() {
    return opname;
  }

  public void setOpname(OpMap.OpName opname) {
    this.opname = opname;
  }

  public int getOpcode() {
    return opcode;
  }

  public void setOpcode(int opcode) {
    this.opcode = opcode;
  }

  public int getOparg() {
    return oparg;
  }

  public void setOparg(int oparg) {
    this.oparg = oparg;
  }

  @Override
  public String toString() {
    return "Instruction{" + "opname=" + opname + ", opcode=" + opcode + ", oparg=" + oparg + '}';
  }
}
