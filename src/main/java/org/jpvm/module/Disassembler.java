package org.jpvm.module;

import java.util.HashSet;
import java.util.Iterator;
import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.bytecode.OpMap;
import org.jpvm.errors.PyException;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.pycParser.PyCodeObject;

public class Disassembler {

  private final PyCodeObject pyCodeObject;
  private final ByteCodeBuffer buf;

  public Disassembler(PyCodeObject pyCodeObject) {
    this.pyCodeObject = pyCodeObject;
    buf = new ByteCodeBuffer(pyCodeObject);
  }

  public void dis() throws PyException {
    HashSet<Integer> enterPoint = new HashSet<>();
    Iterator<Instruction> iterator = buf.iterator();
    for (Instruction ins : buf) {
      if (OpMap.instructions.containsKey(ins.getOpcode())) {
        if (ins.getOpname().toString().toLowerCase().contains("jump"))
          enterPoint.add(ins.getOparg());
      }
    }
    buf.reset(0);
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
          var coConsts = (PyTupleObject) pyCodeObject.getCoConsts();
          if (coConsts.get(ins.getOparg()) instanceof PyCodeObject cb) {
            builder.append(" <CodeObject ").append(cb.getCoName())
                .append(" @0x")
                .append(Integer.toHexString(System.identityHashCode(cb)))
                .append(" ")
                .append(cb.getCoFileName()).append(", line ")
                .append(cb.getCoFirstLineNo()).append(" >");
          } else {
            builder.append(" (").append(coConsts.get(ins.getOparg())).append(")");
          }
        }
        case STORE_NAME, LOAD_NAME, IMPORT_NAME -> {
          var coNames = (PyTupleObject) pyCodeObject.getCoNames();
          builder.append(" (").append(coNames.get(ins.getOparg())).append(")");
        }
      }
      System.out.println(builder);
    }
  }
}
