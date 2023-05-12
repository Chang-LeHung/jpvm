package org.jpvm.module;

import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.pycParser.CodeObject;

import java.util.Iterator;

public class Disassembler {

   private CodeObject codeObject;
   private ByteCodeBuffer buf;

   public Disassembler(CodeObject codeObject) {
      this.codeObject = codeObject;
      buf = new ByteCodeBuffer(codeObject);
   }

   public void dis() {
      Iterator<Instruction> iterator = buf.iterator();
      StringBuilder builder = new StringBuilder();
      while (iterator.hasNext()) {
         Instruction ins = iterator.next();
         if (ins.getOpcode() == 0)
            break;
         builder.delete(0, builder.length());
         builder.append(String.format("%4d", ins.getPos()));
         builder.append(" ");
         builder.append(String.format("%-15s", ins.getOpname()));
         builder.append("\t");
         builder.append(ins.getOparg());
         switch (ins.getOpname()) {
            case LOAD_CONST -> {
               var coConsts = (PyTupleObject) codeObject.getCoConsts();
               if (coConsts.get(ins.getOparg()) instanceof CodeObject) {
                  var cb = (CodeObject)coConsts.get(ins.getOparg());
                  builder.append(" <CodeObject ").append(cb.getCoName())
                      .append(" @0x")
                      .append(Integer.toHexString(System.identityHashCode(cb)))
                      .append(" ")
                      .append(cb.getCoFileName()).append(", line ")
                      .append(cb.getCoFirstLineNo()).append(" >");
               }else {
                  builder.append(" (").append(coConsts.get(ins.getOparg())).append(")");
               }
            }
            case STORE_NAME, LOAD_NAME -> {
               var coNames = (PyTupleObject) codeObject.getCoNames();
               builder.append(" (").append(coNames.get(ins.getOparg())).append(")");
            }
         }
         System.out.println(builder);
      }
   }
}
