package org.jpvm.module;

import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.bytecode.OpMap;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.pycParser.CodeObject;

import java.util.HashSet;
import java.util.Iterator;

public class Disassembler {

   private final CodeObject codeObject;
   private final ByteCodeBuffer buf;

   public Disassembler(CodeObject codeObject) {
      this.codeObject = codeObject;
      buf = new ByteCodeBuffer(codeObject);
   }

   public void dis() {
      HashSet<Integer> enterPoint = new HashSet<>();
      Iterator<Instruction> iterator = buf.iterator();
      for (Instruction ins : buf) {
         if (OpMap.instructions.containsKey(ins.getOpcode())) {
            if (ins.getOpname().toString().toLowerCase().contains("jump"))
               enterPoint.add(ins.getOparg());
         }
      }
      StringBuilder builder = new StringBuilder();
      while (iterator.hasNext()) {
         Instruction ins = iterator.next();
         if (!OpMap.instructions.containsKey(ins.getOpcode())) // for bug fix
            continue;
         builder.delete(0, builder.length());

         if (enterPoint.contains(ins.getPos())) {
            builder.append(" >>");
         }
         builder.append("\t")
             .append(String.format("%4d", ins.getPos()))
             .append(" ")
             .append(String.format("%-15s", ins.getOpname()))
             .append("\t")
             .append(String.format("%3d", ins.getOparg()));
         switch (ins.getOpname()) {
            case LOAD_CONST -> {
               var coConsts = (PyTupleObject) codeObject.getCoConsts();
               if (coConsts.get(ins.getOparg()) instanceof CodeObject cb) {
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
            case STORE_NAME, LOAD_NAME, IMPORT_NAME -> {
               var coNames = (PyTupleObject) codeObject.getCoNames();
               builder.append(" (").append(coNames.get(ins.getOparg())).append(")");
            }
         }
         System.out.println(builder);
      }
   }
}
