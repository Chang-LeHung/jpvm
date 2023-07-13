package org.jpvm.pvm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.bytecode.Instruction;
import org.jpvm.bytecode.OpMap;
import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNameError;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.module.Marshal;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeRichCompare;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.pycParser.PyCodeObject;
import org.jpvm.pycParser.PycReader;
import org.jpvm.python.BuiltIn;

public class EvaluationLoop {

  public static final int FVC_MASK = 0x3;
  public static final int FVC_NONE = 0x0;
  public static final int FVC_STR = 0x1;
  public static final int FVC_REPR = 0x2;
  public static final int FVC_ASCII = 0x3;
  public static final int FVS_MASK = 0x4;
  public static final int FVS_HAVE_SPEC = 0x4;
  private final PyFrameObject frame;
  private final Iterator<Instruction> iterator;
  private final PyTupleObject coNames;
  private final PyDictObject globals;
  private final PyDictObject locals;
  private final PyDictObject builtins;
  private final PyTupleObject consts;
  private final ByteCodeBuffer byteCodeBuffer;
  private PyException error;

  public EvaluationLoop(PyFrameObject frame) {
    this.frame = frame;
    PyCodeObject code = frame.getCode();
    byteCodeBuffer = frame.getByteCodeBuffer();
    iterator = byteCodeBuffer.iterator();
    coNames = (PyTupleObject) code.getCoNames();
    globals = frame.getGlobals();
    locals = frame.getLocals();
    builtins = frame.getBuiltins();
    consts = (PyTupleObject) code.getCoConsts();
  }

  public static void evalModule(PyCodeObject codeObject, PyDictObject namespace)
      throws PyException {
    PyFrameObject frameObject =
        new PyFrameObject(codeObject, PVM.getThreadState().getBuiltins(), namespace, namespace);
    new EvaluationLoop(frameObject).pyEvalFrame();
  }

  public PyTupleObject getArgs(Instruction ins) {
    int size = ins.getOparg();
    PyTupleObject args = new PyTupleObject(size);
    for (int i = 0; i < size; i++) {
      args.set(size - i - 1, frame.pop());
    }
    return args;
  }

  public PyFrameObject getFrame() {
    return frame;
  }

  public PyException getError() {
    return error;
  }

  public Iterator<Instruction> getIterator() {
    return iterator;
  }

  public PyTupleObject getCoNames() {
    return coNames;
  }

  public PyDictObject getGlobals() {
    return globals;
  }

  public PyDictObject getLocals() {
    return locals;
  }

  public PyDictObject getBuiltins() {
    return builtins;
  }

  public PyTupleObject getConsts() {
    return consts;
  }

  public ByteCodeBuffer getByteCodeBuffer() {
    return byteCodeBuffer;
  }

  public PyObject getClassMethod(PyUnicodeObject name) {
    PyObject obj = frame.top();
    Class<? extends PyObject> clazz = obj.getClass();
    try {
      Method meth = clazz.getMethod(name.getData(), PyObject.parameterTypes);
      PyClassMethod annotation = meth.getAnnotation(PyClassMethod.class);
      if (annotation != null) {
        return new PyMethodObject(obj, meth, name.getData());
      }
    } catch (NoSuchMethodException e) {
      error = new PyException("object " + obj.repr() + " not have method " + name.repr());
    }
    error = new PyException("object " + obj.repr() + " not have method " + name.repr());
    return null;
  }

  public PyObject pyEvalFrame() throws PyException {

    // evaluation loop
    while (iterator.hasNext()) {
      InterpreterState is = PVM.getThreadState().getIs();
      if (is.isDropGILRequest()) {
        // release global interpreter lock
        is.dropGIL();
        // require  global interpreter lock
        is.takeGIL();
      }
      Instruction ins = iterator.next();
      try {
        switch (ins.getOpname()) {
          case IMPORT_NAME -> {
            frame.pop();
            frame.pop();
            PyObject name = coNames.get(ins.getOparg());
            PyObject module = PVM.getThreadState().getIs().getModule((PyUnicodeObject) name);
            if (module != null) {
              frame.push(module);
              continue;
            }
            String moduleName = ((PyUnicodeObject) name).getData();
            PyListObject searchPath = PVM.getThreadState().getIs().getSearchPath();
            boolean found = false;
            for (int i = 0; i < searchPath.size(); i++) {
              var path = ((PyUnicodeObject) searchPath.get(i)).getData();
              if (path.endsWith("__pycache__")) {
                File pyDir = new File(path);
                File[] files = pyDir.listFiles();
                if (files == null) continue;
                for (File file : files) {
                  if (file.getName().startsWith(moduleName) && file.getName().endsWith(".pyc")) {
                    PycReader reader = new PycReader(file.getAbsolutePath());
                    try {
                      reader.doParse();
                      PyCodeObject code = reader.getCodeObject();
                      PyModuleObject newModule = new PyModuleObject((PyUnicodeObject) name);
                      evalModule(code, newModule.getDict());
                      frame.push(newModule);
                      PVM.getThreadState().getIs().addModule((PyUnicodeObject) name, newModule);
                      found = true;
                      break;
                    } catch (IOException ignore) {
                    }
                  }
                }
              } else {
                path = path.replace("/", ".");
                PyModuleObject res =
                    Utils.loadClass(path + "." + moduleName, (PyUnicodeObject) name);
                if (res != null) {
                  found = true;
                  PVM.getThreadState().getIs().addModule((PyUnicodeObject) name, res);
                  frame.push(res);
                  break;
                }
                res =
                    Utils.loadClass(
                        path + "." + moduleName + ".PyModuleMain", (PyUnicodeObject) name);
                if (res != null) {
                  found = true;
                  PVM.getThreadState().getIs().addModule((PyUnicodeObject) name, res);
                  frame.push(res);
                  break;
                }
              }
            }
            if (!found) {
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.ImportError, "can not find a module named " + moduleName);
            }
          }
          case IMPORT_FROM -> {
            var name = coNames.get(ins.getOparg());
            PyObject top = frame.top();
            PyObject res = Utils.loadFiled(top, (PyUnicodeObject) name);
            if (res != null) {
              frame.push(res);
              continue;
            }
            res = Utils.loadClassMethod(top, (PyUnicodeObject) name);
            if (res != null) {
              frame.push(res);
              continue;
            }
            res = top.getAttr(name);
            if (res != null) {
              frame.push(res);
              continue;
            }
            PyErrorUtils.pyErrorFormat(
                PyErrorUtils.ImportError, "can not import " + name.repr() + " from " + top.repr());
          }
          case LOAD_CONST -> frame.push(consts.get(ins.getOparg()));
          case STORE_NAME -> {
            PyObject top = frame.pop();
            PyObject o = coNames.get(ins.getOparg());
            locals.put(o, top);
          }
          case DELETE_NAME -> {
            PyObject o = coNames.get(ins.getOparg());
            locals.remove(o);
          }
          case STORE_GLOBAL -> {
            PyObject top = frame.pop();
            PyObject o = coNames.get(ins.getOparg());
            globals.put(o, top);
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
          case LOAD_ATTR -> {
            PyObject top = frame.top();
            var name = (PyUnicodeObject) coNames.get(ins.getOparg());
            PyObject attr = top.getAttr(name);
            if (null != attr) {
              frame.pop();
              frame.push(attr);
              continue;
            }
            PyObject res = getClassMethod(name);
            if (res != null) {
              frame.pop();
              frame.push(res);
              continue;
            }
            error = null;
            PyObject object = Utils.loadFiled(top, name);
            if (object != null) {
              frame.pop();
              frame.push(object);
              continue;
            }
            PyErrorUtils.pyErrorFormat(
                PyErrorUtils.AttributeError,
                "can not find attribute " + name.repr() + " in " + top.repr());
          }
          case STORE_ATTR -> {
            var name = (PyUnicodeObject) coNames.get(ins.getOparg());
            PyObject object = frame.pop();
            object.setAttr(name, frame.pop());
          }
          case STORE_FAST -> frame.setLocal(ins.getOparg(), frame.pop());
          case LOAD_FAST -> frame.push(frame.getLocal(ins.getOparg()));
          case DELETE_FAST -> {
            int arg = ins.getOparg();
            frame.setLocal(arg, null);
          }
          case LOAD_DEREF -> {
            PyObject cell = frame.getFreeVars(ins.getOparg());
            frame.push(cell);
          }
          case STORE_DEREF -> {
            PyObject cell = frame.pop();
            frame.setFreeVars(ins.getOparg(), cell);
          }
          case LOAD_CLOSURE -> {
            PyObject cell = frame.getFreeVarsCell(ins.getOparg());
            frame.push(cell);
          }
          case LOAD_METHOD -> {
            var name = (PyUnicodeObject) coNames.get(ins.getOparg());
            PyObject obj = frame.pop();
            PyObject method = obj.getMethod(name);
            if (method != null && method != BuiltIn.None) {
              frame.push(method);
              continue;
            }
            PyErrorUtils.pyErrorFormat(
                PyErrorUtils.AttributeError,
                "object + " + obj.repr() + " not have method " + name.repr());
          }
          case CALL_METHOD -> {
            PyTupleObject args = getArgs(ins);
            PyObject method = frame.pop();
            if (method instanceof PyMethodObject) {
              frame.push(Abstract.abstractCall(method, null, args, null, frame));
            } else if (method instanceof PyTypeType type) {
              type.call(null, args, null);
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.AttributeError, "object " + method.repr() + " can not be called");
          }
          case CALL_FUNCTION -> {
            PyTupleObject args = getArgs(ins);
            PyObject pop = frame.pop();
            PyObject object = Abstract.abstractCall(pop, null, args, null, frame);
            assert object != null;
            frame.push(object);
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
            for (int i = args.size() - 1; i >= 0; i--) args.set(i, frame.pop());
            PyObject callable = frame.pop();
            PyObject object = Abstract.abstractCall(callable, null, args, kwArgs, frame);
            frame.push(object);
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
          case UNPACK_SEQUENCE -> {
            int size = ins.getOparg();
            PyObject top = frame.pop();
            if (top instanceof PyTupleObject tuple) {
              if (tuple.size() == size) {
                for (int i = 0; i < tuple.size(); i++)
                  // push argument from right to left
                  frame.push(tuple.get(size - 1 - i));
                continue;
              }
            } else if (top instanceof PyListObject list) {
              if (list.size() == size) {
                for (int i = 0; i < list.size(); i++)
                  // push argument from right to left
                  frame.push(list.get(size - 1 - i));
                continue;
              }
            } else if (top instanceof TypeDoIterate itr) {
              if (itr.size() == size) {
                frame.increaseStackPointer(size);
                for (int i = 0; i < size; i++) {
                  frame.setTop(i + 1, itr.next());
                }
                continue;
              }
            }
            PyErrorUtils.pyErrorFormat(
                PyErrorUtils.TypeError,
                top.repr() + " can not be unpacked into " + size + " objects");
          }
          case LIST_APPEND -> {
            PyObject top = frame.pop();
            PyListObject list = (PyListObject) frame.top(ins.getOparg());
            list.append(top);
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
          case JUMP_ABSOLUTE -> byteCodeBuffer.reset(ins.getOparg());
          case BUILD_STRING -> {
            int size = ins.getOparg();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < size; i++) {
              PyObject object = frame.get(frame.getUsed() - size + i);
              builder.append(object.str().getData());
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
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, "require an iterator on stack top");
            }
          }
          case GET_ITER -> {
            PyObject pop = frame.pop();
            if (pop instanceof TypeIterable itr) {
              frame.push((PyObject) itr.getIterator());
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, pop.repr() + " is not a iterable object");
          }
          case BINARY_MULTIPLY -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.multiply(left, right);
            frame.push(res);
          }
          case BINARY_ADD -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.add(left, right);
            frame.push(res);
          }
          case BINARY_SUBTRACT -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.sub(left, right);
            frame.push(res);
          }
          case INPLACE_ADD -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceAdd(left, right);
            frame.push(res);
          }
          case INPLACE_AND -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceAnd(left, right);
            frame.push(res);
          }
          case INPLACE_LSHIFT -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceLshift(left, right);
            frame.push(res);
          }
          case INPLACE_MULTIPLY -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceMul(left, right);
            frame.push(res);
          }
          case INPLACE_OR -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceOr(left, right);
            frame.push(res);
          }
          case INPLACE_SUBTRACT -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceSub(left, right);
            frame.push(res);
          }
          case INPLACE_POWER -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplacePow(left, right);
            frame.push(res);
          }
          case INPLACE_FLOOR_DIVIDE -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceFloorDiv(left, right);
            frame.push(res);
          }
          case INPLACE_RSHIFT -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceRshift(left, right);
            frame.push(res);
          }
          case INPLACE_TRUE_DIVIDE -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceTrueDiv(left, right);
            frame.push(res);
          }
          case INPLACE_MATRIX_MULTIPLY -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceMatrixMul(left, right);
            frame.push(res);
          }
          case INPLACE_MODULO -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceMod(left, right);
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
              frame.push(num.pos());
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, top.repr() + " not support operator +");
          }
          case UNARY_NEGATIVE -> {
            PyObject top = frame.pop();
            if (top instanceof PyNumberMethods num) {
              frame.push(num.neg());
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, top.repr() + " not support operator -");
          }
          case UNARY_INVERT -> {
            PyObject top = frame.pop();
            if (top instanceof PyNumberMethods num) {
              frame.push(num.invert());
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, top.repr() + " not support operator ~");
          }
          case UNARY_NOT -> {
            PyObject top = frame.pop();
            if (Abstract.isTrue(top).isTrue()) frame.push(BuiltIn.False);
            else frame.push(BuiltIn.True);
          }
          case BINARY_POWER -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.pow(left, right);
            frame.push(res);
          }
          case BINARY_MATRIX_MULTIPLY -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.matrixMul(left, right);
            frame.push(res);
          }
          case BINARY_TRUE_DIVIDE -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.trueDiv(left, right);
            frame.push(res);
          }
          case BINARY_FLOOR_DIVIDE -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.floorDiv(left, right);
            frame.push(res);
          }
          case BINARY_MODULO -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.mod(left, right);
            frame.push(res);
          }
          case BUILD_SLICE -> {
            PyObject t1 = frame.pop();
            PyObject t2 = frame.pop();
            if (ins.getOparg() == 2) {
              PySliceObject sliceObject = new PySliceObject(t2, t1, PyLongObject.getLongObject(1));
              frame.push(sliceObject);
            } else if (ins.getOparg() == 3) {
              PySliceObject sliceObject = new PySliceObject(frame.pop(), t2, t1);
              frame.push(sliceObject);
            }
          }
          case BINARY_SUBSCR -> {
            PyObject sub = frame.pop();
            PyObject container = frame.pop();
            PyObject item = Abstract.getItem(container, sub);
            frame.push(item);
          }
          case STORE_SUBSCR -> {
            PyObject key = frame.pop();
            PyObject obj = frame.pop();
            PyObject val = frame.pop();
            Abstract.assignItem(obj, key, val);
          }
          case BINARY_LSHIFT -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.lshift(left, right);
            frame.push(res);
          }
          case BINARY_RSHIFT -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.rshift(left, right);
            frame.push(res);
          }
          case BINARY_AND -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.and(left, right);
            frame.push(res);
          }
          case BINARY_XOR -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.xor(left, right);
            frame.push(res);
          }
          case INPLACE_XOR -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.inplaceXor(left, right);
            frame.push(res);
          }
          case BINARY_OR -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject res = Abstract.or(left, right);
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
          case BUILD_SET -> {
            int size = ins.getOparg();
            PySetObject setObject = new PySetObject();
            for (int i = 0; i < size; i++) {
              setObject.add(frame.pop());
            }
            frame.push(setObject);
          }
          case BUILD_MAP -> {
            PyDictObject dictObject = new PyDictObject();
            int size = ins.getOparg();
            for (int i = 0; i < size; i++) {
              PyObject val = frame.pop();
              PyObject key = frame.pop();
              dictObject.put(key, val);
            }
            frame.push(dictObject);
          }
          case JUMP_FORWARD -> {
            int oparg = ins.getOparg();
            byteCodeBuffer.increase(oparg);
          }
          case POP_JUMP_IF_FALSE -> {
            PyObject pop = frame.pop();
            if (pop instanceof PyBoolObject b) {
              if (b.isFalse()) byteCodeBuffer.reset(ins.getOparg());
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, "POP_JUMP_IF_FALSE require boo on stack top");
          }
          case POP_JUMP_IF_TRUE -> {
            PyObject pop = frame.pop();
            if (pop instanceof PyBoolObject b) {
              if (b.isTrue()) byteCodeBuffer.reset(ins.getOparg());
            }
            PyErrorUtils.pyErrorFormat(
                PyErrorUtils.TypeError, "POP_JUMP_IF_FALSE require boo on stack top");
          }
          case COMPARE_OP -> {
            PyObject right = frame.pop();
            PyObject left = frame.pop();
            PyObject result;
            switch (ins.getOparg()) {
              case TypeRichCompare.Py_LT -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.Py_LT);
              case TypeRichCompare.Py_LE -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.Py_LE);
              case TypeRichCompare.Py_EQ -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.Py_EQ);
              case TypeRichCompare.Py_NE -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.Py_NE);
              case TypeRichCompare.Py_GT -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.Py_GT);
              case TypeRichCompare.Py_GE -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.Py_GE);
              case TypeRichCompare.PyCmp_IN -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IN);
              case TypeRichCompare.PyCmp_NOT_IN -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_NOT_IN);
              case TypeRichCompare.PyCmp_IS -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IS);
              case TypeRichCompare.PyCmp_IS_NOT -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_IS_NOT);
              case TypeRichCompare.PyCmp_EXC_MATCH -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_EXC_MATCH);
              case TypeRichCompare.PyCmp_BAD -> result =
                  Abstract.compare(left, right, TypeRichCompare.Operator.PyCmp_BAD);
              default -> throw new PyException("Unknow COMPARE_OP operator" + ins.getOparg());
            }
            frame.push(result);
          }
          case LOAD_BUILD_CLASS -> {
            PyObject res =
                builtins.get(
                    PyUnicodeObject.getOrCreateFromInternStringPool("__build_class__", true));
            frame.push(res);
          }
          case MAKE_FUNCTION -> {
            PyObject qualname = frame.pop();
            if (!(qualname instanceof PyUnicodeObject)) {
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, "qualname require PyUnicodeObject");
              break;
            }
            PyCodeObject codeObject = (PyCodeObject) frame.pop();
            PyFunctionObject function =
                new PyFunctionObject(codeObject, globals, (PyUnicodeObject) qualname);
            // Simply for compatibility with previous code
            PyUnicodeObject module = frame.getModuleName();
            if (module != null) {
              function.setFuncModule(module);
            }
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
            if (top == BuiltIn.False) byteCodeBuffer.reset(ins.getOparg());
            else frame.pop();
          }
          case JUMP_IF_TRUE_OR_POP -> {
            PyObject top = frame.top();
            if (top == BuiltIn.True) byteCodeBuffer.reset(ins.getOparg());
            else frame.pop();
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
          case NOP -> {}
          case YIELD_VALUE -> {
            PyObject res = frame.top();
            if ((frame.getCode().getCoFlags() & Marshal.CO_GENERATOR) != 0) return res;
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "yield value is not supported");
          }
            /*
             * GET_YIELD_FROM_ITER If TOS is a generator iterator or
             * coroutine object it is left as is.
             * Otherwise, implements TOS = iter(TOS).
             */
          case GET_YIELD_FROM_ITER -> {
            PyObject top = frame.top();
            if (!(top instanceof PyGeneratorObject)) {
              if (top instanceof TypeIterable iter) {
                frame.setTop(1, (PyObject) iter.getIterator());
                continue;
              }
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, "require a generator or coroutine or iterable object");
            }
          }
          case YIELD_FROM -> {
            PyObject top = frame.pop();
            PyObject gen = frame.top();
            if (gen instanceof PyGeneratorObject g) {
              frame.increaseStackPointer(1);
              PyObject res = g.start(top);
              if (res == BuiltIn.PyExcStopIteration) continue;
              byteCodeBuffer.decrease(Instruction.sizeofByteCode);
              return res;
            } else
              PyErrorUtils.pyErrorFormat(
                  PyErrorUtils.TypeError, "require a generator or coroutine or iterable object");
          }
          default -> throw new PyException(
              "not support opcode " + OpMap.instructions.get(ins.getOpcode()) + " currently", true);
        }
      } catch (PyException e) {
        ThreadState ts = PVM.getThreadState();
        PyObject curExcValue = ts.getCurExcValue();
        PyObject curExcType = ts.getCurExcType();
        PyObject curExcTrace = ts.getCurExcTrace();
        if (curExcValue != null) {

        } else {
          System.out.println(errorMessageTip(ins));
          e.printStackTrace();
        }
      }
      // check top whether is PyExcStopIteration
      if (frame.getUsed() > 0 && frame.top() == BuiltIn.PyExcStopIteration) {
        throw new PyException(
            "Execution error with op " + ins.getOpname() + " PyExcStopIteration is thrown", false);
      }
    }
    if (frame.hasArgs()) return frame.pop();
    return null;
  }

  private String errorMessageTip(Instruction ins) {
    return "Execution error with op "
        + ins.getOpname()
        + " in module "
        + frame.getCode().getCoName()
        + ":\n"
        + ">>>\n"
        + error.getMessage()
        + "\n<<<";
  }

  private void loadFromGlobal(
      PyFrameObject frame, PyDictObject globals, PyDictObject builtins, PyObject name) {
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
