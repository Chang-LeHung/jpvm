package org.jpvm.pvm;

import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.bytecode.OpMap;
import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNameError;
import org.jpvm.errors.PyTypeError;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyFunctionType;
import org.jpvm.protocols.PyNumberMethods;
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
            loadFromGlobal(frame, globals, builtins, name);
          } else {
            frame.push(v);
          }
        }
        case LOAD_GLOBAL -> {
          PyObject name = coNames.get(ins.getOparg());
          loadFromGlobal(frame, globals, builtins, name);
        }
        case STORE_FAST -> frame.setLocal(ins.getOparg(), frame.pop());
        case LOAD_FAST -> frame.push(frame.getLocal(ins.getOparg()));
        case CALL_FUNCTION -> {
          int size = ins.getOparg();
          PyTupleObject args = new PyTupleObject(size);
          for (int i = 0; i < size; i++) {
            args.set(size - i - 1, frame.pop());
          }
          PyObject pop = frame.pop();
          try {
            PyObject object = Abstract.abstractCall(pop, null, args, null, frame);
            frame.push(object);
          } catch (PyException e) {
            error = e;
          }
          // other callable object to be implemented
        }
        case BUILD_CONST_KEY_MAP -> {
          var keys = (PyTupleObject) frame.pop();
          PyDictObject dict = new PyDictObject();
          int size = keys.size();
          for (int i = 0; i < size; i++) {
            dict.put(keys.get(size - 1 - i), frame.pop());
          }
          frame.push(dict);
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
          try {
            PyObject object = Abstract.abstractCall(callable, null, args, kwArgs, frame);
            frame.push(object);
          } catch (PyException e) {
            error = e;
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
          PyObject spec = have_fmt_spec ? frame.pop() : null;
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
            } else {
              frame.pop();
              byteCodeBuffer.increase(ins.getOparg());
            }
          } else {
            error = new PyTypeNotMatch("require an iterator on stack top");
            error.setInternalError(true);
          }
        }
        case GET_ITER -> {
          PyObject pop = frame.pop();
          if (pop instanceof TypeIterable itr) {
            frame.push((PyObject) itr.getIterator());
          } else error = new PyException(pop.repr() + " is not a iterable object");
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
        case BINARY_SUBTRACT -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.sub(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case ROT_TWO -> {
          PyObject o1 = frame.pop();
          PyObject o2 = frame.pop();
          frame.push(o1);
          frame.push(o2);
        }
        case ROT_THREE -> {
          PyObject o1 = frame.pop();
          PyObject o2 = frame.pop();
          PyObject o3 = frame.pop();
          frame.push(o1);
          frame.push(o2);
          frame.push(o3);
        }
        case ROT_FOUR -> {
          PyObject o1 = frame.pop();
          PyObject o2 = frame.pop();
          PyObject o3 = frame.pop();
          PyObject o4 = frame.pop();
          frame.push(o1);
          frame.push(o2);
          frame.push(o3);
          frame.push(o4);
        }
        case DUP_TOP -> {
          PyObject top = frame.top();
          frame.push(top);
        }
        case DUP_TOP_TWO -> {
          PyObject o1 = frame.top(1);
          PyObject o2 = frame.top(2);
          frame.push(o2);
          frame.push(o1);
        }
        case UNARY_POSITIVE -> {
          PyObject top = frame.pop();
          if (top instanceof PyNumberMethods num) {
            try {
              frame.push(num.pos());
            }catch (PyException e) {
              error = e;
            }
          }else
            error = new PyTypeError(top.repr() + " not support operator +");
        }
        case UNARY_NEGATIVE -> {
          PyObject top = frame.pop();
          if (top instanceof PyNumberMethods num) {
            try {
              frame.push(num.neg());
            }catch (PyException e) {
              error = e;
            }
          }else
            error = new PyTypeError(top.repr() + " not support operator -");
        }
        case UNARY_INVERT -> {
          PyObject top = frame.pop();
          if (top instanceof PyNumberMethods num) {
            try {
              frame.push(num.invert());
            }catch (PyException e) {
              error = e;
            }
          }else
            error = new PyTypeError(top.repr() + " not support operator ~");
        }
        case UNARY_NOT -> {
          PyObject top = frame.pop();
          if ( Abstract.isTrue(top).isTrue())
            frame.push(BuiltIn.False);
          else
            frame.push(BuiltIn.True);
        }
        case BINARY_POWER -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.pow(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_MATRIX_MULTIPLY -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.matrixMul(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_TRUE_DIVIDE -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.trueDiv(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_FLOOR_DIVIDE -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.floorDiv(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_MODULO -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.mod(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BUILD_SLICE -> {
          PyObject t1 = frame.pop();
          PyObject t2 = frame.pop();
          if (ins.getOparg() == 2) {
            PySliceObject sliceObject = new PySliceObject(t2, t1, PyLongObject.getLongObject(1));
            frame.push(sliceObject);
          }else if (ins.getOparg() == 3) {
            PySliceObject sliceObject = new PySliceObject(frame.pop(), t2, t1);
            frame.push(sliceObject);
          }
        }
        case BINARY_SUBSCR -> {
          PyObject sub = frame.pop();
          PyObject container = frame.pop();
          try {
            PyObject item = Abstract.getItem(container, sub);
            if (item == BuiltIn.notImplemented)
              error = new PyException("can not apply BINARY_SUBSCR on " + container + " and " + sub.repr());
            else
              frame.push(item);
          }catch (PyException e) {
            error = e;
          }
        }
        case BINARY_LSHIFT -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.lshift(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_RSHIFT -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.rshift(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_AND -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.and(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_XOR -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.xor(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case INPLACE_XOR -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.inplaceXor(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BINARY_OR -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject res = Abstract.or(left, right);
          if (res == BuiltIn.notImplemented) {
            error = new PyException("can not apply add on " + left.repr() + " and " + right.repr());
          }
          frame.push(res);
        }
        case BUILD_LIST -> {
          int size = ins.getOparg();
          PyListObject listObject = new PyListObject();
          int used = frame.getUsed();
          for (int i = 0; i < size; ++i) {
            listObject.append(frame.get(used - size + i));
          }
          frame.decreaseStackPointer(size);
          frame.push(listObject);
        }
        case JUMP_FORWARD -> {
          int oparg = ins.getOparg();
          byteCodeBuffer.increase(oparg);
        }
        case POP_JUMP_IF_FALSE -> {
          PyObject pop = frame.pop();
          if (pop instanceof PyBoolObject b) {
            if (b.isFalse())
              byteCodeBuffer.reset(ins.getOparg());
          } else
            error = new PyException("POP_JUMP_IF_FALSE require boo on stack top");
        }
        case COMPARE_OP -> {
          PyObject right = frame.pop();
          PyObject left = frame.pop();
          PyObject result;
          try {
            switch (ins.getOparg()) {
              case TypeRichCompare.Py_LT -> result = Abstract.compare(left, right, TypeRichCompare.Operator.Py_LT);
              case TypeRichCompare.Py_LE -> result = Abstract.compare(left, right, TypeRichCompare.Operator.Py_LE);
              case TypeRichCompare.Py_EQ -> result = Abstract.compare(left, right, TypeRichCompare.Operator.Py_EQ);
              case TypeRichCompare.Py_NE -> result = Abstract.compare(left, right, TypeRichCompare.Operator.Py_NE);
              case TypeRichCompare.Py_GT -> result = Abstract.compare(left, right, TypeRichCompare.Operator.Py_GT);
              case TypeRichCompare.Py_GE -> result = Abstract.compare(left, right, TypeRichCompare.Operator.Py_GE);
              case TypeRichCompare.PyCmp_IN ->
                  result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IN);
              case TypeRichCompare.PyCmp_NOT_IN ->
                  result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_NOT_IN);
              case TypeRichCompare.PyCmp_IS ->
                  result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IS);
              case TypeRichCompare.PyCmp_IS_NOT ->
                  result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IS_NOT);
              case TypeRichCompare.PyCmp_EXC_MATCH ->
                  result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_EXC_MATCH);
              case TypeRichCompare.PyCmp_BAD ->
                  result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_BAD);
              default -> throw new PyException("Unknow COMPARE_OP operator" + ins.getOparg());
            }
            frame.push(result);
          } catch (PyException e) {
            error = e;
          }
        }
        case MAKE_FUNCTION -> {
          PyObject qualname = frame.pop();
          if (!(qualname instanceof PyUnicodeObject)) {
            error = new PyException("qualname require PyUnicodeObject", true);
            break;
          }
          PyCodeObject codeObject = (PyCodeObject) frame.pop();
          var type = (PyFunctionType) PyFunctionObject.type;
          // just for debugging to avoid cycle reference, idea will get stuck for toString method
          PyDictObject dict = new PyDictObject();
          dict.addAll(globals);
          PyFunctionObject function = type.createFunction(codeObject, dict, (PyUnicodeObject) qualname);
          int oparg = ins.getOparg();
          if ((oparg & 0x08) != 0) {
            function.setFuncClosure(frame.pop());
          }
          if ((oparg & 0x04) != 0) {
            function.setAnnotation(frame.pop());
          }
          if ((oparg & 0x02) != 0) {
            function.setFuncKwDefaults(frame.pop());
          }
          if ((oparg & 0x01) != 0) {
            function.setFuncDefaults(frame.pop());
          }
          frame.push(function);
        }
        case JUMP_IF_FALSE_OR_POP -> {
          PyObject top = frame.top();
          if (top == BuiltIn.False)
            byteCodeBuffer.reset(ins.getOparg());
          else
            frame.pop();
        }
        case JUMP_IF_TRUE_OR_POP -> {
          PyObject top = frame.top();
          if (top == BuiltIn.True)
            byteCodeBuffer.reset(ins.getOparg());
          else
            frame.pop();
        }
        case BUILD_TUPLE -> {
          int size = ins.getOparg();
          PyTupleObject tuple = new PyTupleObject(size);
          int used = frame.getUsed();
          for (int i = 0; i < size; ++i) {
            tuple.set(i, frame.get(used - size + i));
          }
          frame.decreaseStackPointer(size);
          frame.push(tuple);
        }
        default -> throw new PyException("not support opcode " + OpMap.instructions.get(ins.getOpcode()) + " currently", true);
      }
      if (error != null) throw new PyException("Execution error with op " + ins.getOpname() + " " + error.getMessage(), error.isInternalError());
    }
    if (frame.hasArgs()) return frame.pop();
    return BuiltIn.None;
  }

  private void loadFromGlobal(PyFrameObject frame, PyDictObject globals, PyDictObject builtins, PyObject name) {
    PyObject v;
    v = globals.get(name);
    if (null == v) {
      v = builtins.get(name);
      if (v == null) {
        error = new PyNameError("can not find variable " + name.repr());
      } else {
        frame.push(v);
      }
    } else {
      frame.push(v);
    }
  }
}
