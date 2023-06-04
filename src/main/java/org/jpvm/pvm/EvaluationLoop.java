package org.jpvm.pvm;

import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.python.BuiltIn;

import java.util.Iterator;

public class EvaluationLoop {

  public static final int FVC_MASK = 0x3;
  public static final int FVC_NONE = 0x0;
  public static final int FVC_STR = 0x1;
  public static final int FVC_REPR = 0x2;
  public static final int FVC_ASCII = 0x3;
  public static final int FVS_MASK = 0x4;
  public static final int FVS_HAVE_SPEC = 0x4;

  private PyException error;


  public PyObject pyEvalFrame(PyFrameObject frame) throws PyException {
    PyCodeObject code = frame.getCode();
    PyTupleObject coNames = (PyTupleObject) code.getCoNames();
    PyTupleObject coVarNames = (PyTupleObject) code.getCoVarNames();
    ByteCodeBuffer byteCodeBuffer = new ByteCodeBuffer(code);
    PyDictObject globals = frame.getGlobals();
    PyDictObject locals = frame.getLocals();
    PyDictObject builtins = frame.getBuiltins();
    Iterator<Instruction> iterator = byteCodeBuffer.iterator();
    PyTupleObject consts = (PyTupleObject) code.getCoConsts();

    // evaluation loop
    while (iterator.hasNext()) {
      Instruction ins = iterator.next();
      switch (ins.getOpname()) {
        case LOAD_CONST -> frame.push(consts.get(ins.getOparg()));
        case STORE_NAME -> {
          PyObject top = frame.pop();
          PyObject o = coNames.get(ins.getOparg());
          locals.put(o, top);
        }
        case LOAD_NAME -> {
          PyObject name = coNames.get(ins.getOparg());
          PyObject v = locals.get(name);
          if (null == v) {
            v = globals.get(name);
            if (null == v) {
              v = builtins.get(name);
              if (v == null) {
                error = new PyException("not find variable " + name.toString());
              } else {
                frame.push(v);
              }
            } else {
              frame.push(v);
            }
          } else {
            frame.push(v);
          }
        }
        case CALL_FUNCTION -> {
          int size = ins.getOparg();
          PyTupleObject args = new PyTupleObject(size);
          for (int i = 0; i < size; i++) {
            args.set(size - i - 1, frame.pop());
          }
          PyObject pop = frame.pop();
          if (pop instanceof PyNativeMethodObject nativeMethodObject) {
            try {
              frame.push(nativeMethodObject.call(null, args, null));
            } catch (PyNotImplemented e) {
              error = e;
              break;
            }
          }else if (pop instanceof PyTypeType t) {
            PyObject result = t.call(null, args, null);
            frame.push(result);
          }
          // other callable object to be implemented
        }
        case CALL_FUNCTION_KW -> {
          PyObject pop = frame.pop();
          if (!(pop instanceof PyTupleObject tuple)) {
            error = new PyTypeNotMatch("CALL_FUNCTION_KW stack top requires PyTupleObject");
            break;
          }
          PyDictObject kwArgs = new PyDictObject();
          for (int i = tuple.size() - 1; i >= 0; i--) {
            kwArgs.put(tuple.get(i), frame.pop());
          }
          PyTupleObject args = new PyTupleObject(ins.getOparg() - tuple.size());
          for (int i = args.size() - 1; i >= 0; i--)
            args.set(i, frame.pop());
          PyObject callable = frame.pop();
          if (callable instanceof PyNativeMethodObject nativeMethodObject) {
            try {
              frame.push(nativeMethodObject.call(null, args, kwArgs));
            } catch (PyNotImplemented e) {
              error = e;
              break;
            }
          }
          // other callable object to be implemented
        }
        case POP_TOP -> frame.pop();
        case RETURN_VALUE -> {
          return frame.pop();
        }
        case FORMAT_VALUE -> {
          int oparg = ins.getOparg();
          boolean have_fmt_spec = (oparg & FVS_MASK) == FVS_HAVE_SPEC;
          PyObject sepc = have_fmt_spec ? frame.pop() : null;
          PyObject val = frame.pop();
          switch (oparg & FVC_MASK) {
            case FVC_NONE -> frame.push(val);
            case FVC_STR -> frame.push(val.str());
            case FVC_REPR -> frame.push(val.repr());
            case FVC_ASCII -> frame.push(new PyUnicodeObject(val.toString()));
          }
        }
        case JUMP_ABSOLUTE -> {
          byteCodeBuffer.reset(ins.getOparg());
        }
        case BUILD_STRING -> {
          int size = ins.getOparg();
          StringBuilder builder = new StringBuilder();
          for (int i = 0; i < size; i++) {
            PyObject object = frame.get(frame.getUsed() - size + i);
            builder.append(object.repr().getData());
          }
          frame.decreaseStackPointer(size);
          frame.push(new PyUnicodeObject(builder.toString()));
        }
        case FOR_ITER -> {
          PyObject top = frame.top();
          if (top instanceof TypeDoIterate itr) {
            PyObject next = itr.next();
            if (next != BuiltIn.PyExcStopIteration) {
              frame.push(next);
            }else {
              frame.pop();
              byteCodeBuffer.increase(ins.getOparg());
            }
          }else {
            error = new PyTypeNotMatch("require an iterator on stack top");
            error.setInternalError(true);
          }
        }
        case GET_ITER -> {
          PyObject pop = frame.pop();
          if (pop instanceof TypeIterable itr) {
            frame.push((PyObject) itr.getIterator());
          } else
            error = new PyException(pop.repr() + " is not a iterable object");
        }
        case BINARY_MULTIPLY -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.multiply(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply multiply on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_ADD -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.add(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        default -> throw new PyException("not support opcode " + ins.getOpcode(), true);
      }
      if (error != null)
        throw new PyException(error.getMessage(), error.isInternalError());
    }
    if (frame.hasArgs()) return frame.pop();
    return BuiltIn.None;
  }
}
