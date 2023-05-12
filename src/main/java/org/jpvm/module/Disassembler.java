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
         builder.delete(0, builder.length());
         Instruction ins = iterator.next();
         builder.append(ins.getPos());
         builder.append(" ");
         builder.append(ins.getOpname());
         builder.append("\t");
         builder.append(ins.getOparg());
         switch (ins.getOpname()) {
            case LOAD_CONST:
               var coConsts = (PyTupleObject) codeObject.getCoConsts();
               builder.append(" (").append(coConsts.get(ins.getOparg()).toString()).append(")");
            case STORE_NAME:
               var coNames = (PyTupleObject)codeObject.getCoVarNames();
//               builder.append(" (" + coNames.get(ins.getOparg()) + ")");
         }
         System.out.println(builder);
      }
   }
}
