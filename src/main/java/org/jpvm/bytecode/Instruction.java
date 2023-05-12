package org.jpvm.bytecode;

public class Instruction {

   private int pos;
   private OpMap.OpName opname;
   private int opcode;
   private int oparg;

   public Instruction() {
   }

   public int getPos() {
      return pos;
   }

   public void setPos(int pos) {
      this.pos = pos;
   }

   public Instruction(OpMap.OpName opname, int opcode, int oparg) {
      this.opname = opname;
      this.opcode = opcode;
      this.oparg = oparg;
   }

   public void setOpname(OpMap.OpName opname) {
      this.opname = opname;
   }

   public void setOpcode(int opcode) {
      this.opcode = opcode;
   }

   public void setOparg(int oparg) {
      this.oparg = oparg;
   }

   public OpMap.OpName getOpname() {
      return opname;
   }

   public int getOpcode() {
      return opcode;
   }

   public int getOparg() {
      return oparg;
   }
}
