package org.jpvm.pvm;

import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.errors.PyException;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.objects.*;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyFunctionType;
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
          try {
            PyObject object = Abstract.abstractCall(pop, null, args, null);
            frame.push(object);
          } catch (PyException e) {
            error = e;
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
          try {
            PyObject object = Abstract.abstractCall(callable, null, args, kwArgs);
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
          }
          else
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
              case TypeRichCompare.PyCmp_IN -> result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IN);
              case TypeRichCompare.PyCmp_NOT_IN -> result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_NOT_IN);
              case TypeRichCompare.PyCmp_IS -> result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IS);
              case TypeRichCompare.PyCmp_IS_NOT -> result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IS_NOT);
              case TypeRichCompare.PyCmp_EXC_MATCH -> result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_EXC_MATCH);
              case TypeRichCompare.PyCmp_BAD -> result = Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_BAD);
              default -> throw new PyException("Unknow COMPARE_OP operator" + ins.getOparg());
            }
            frame.push(result);
          }catch (PyException e) {
            error = e;
          }
        }
        case MAKE_FUNCTION -> {
          PyObject qualname = frame.pop();
          if (! (qualname instanceof PyUnicodeObject)) {
            error = new PyException("qualname require PyUnicodeObject", true);
            break;
          }
          PyCodeObject codeObject = (PyCodeObject)frame.pop();
          var type = (PyFunctionType)PyFunctionObject.type;
          PyFunctionObject function = type.createFunction(codeObject, frame.getGlobals(), (PyUnicodeObject) qualname);
          frame.push(function);
        }
        default -> throw new PyException("not support opcode " + ins.getOpcode(), true);
      }
      if (error != null) throw new PyException(error.getMessage(), error.isInternalError());
    }
    if (frame.hasArgs()) return frame.pop();
    return BuiltIn.None;
  }
}
