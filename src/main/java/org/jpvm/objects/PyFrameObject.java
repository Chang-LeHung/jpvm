package org.jpvm.objects;

import java.util.Arrays;
import java.util.Stack;
import org.jpvm.bytecode.ByteCodeBuffer;
import org.jpvm.exceptions.TryBlockHandler;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.types.PyFrameType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.pycParser.PyCodeObject;

public class PyFrameObject extends PyObject {

  public static final PyTypeType type = new PyFrameType();
  private final PyCodeObject code;
  /** value stack */
  private final PyObject[] stack;

  private final PyObject[] localPlus;
  private final ByteCodeBuffer byteCodeBuffer;
  private int used;
  private PyDictObject builtins;
  private PyDictObject globals;
  private PyDictObject locals;
  /** shows how many slots of stack have been used */
  private boolean isExecuting;

  private PyFrameObject back;

  private PyFunctionObject func;
  private final PyObject[] cells;

  private Stack<TryBlockHandler> tryBlockHandlerStack;

  public PyFrameObject(
      PyCodeObject code, PyDictObject builtins, PyDictObject globals, PyFrameObject back) {
    assert code != null;
    this.code = code;
    byteCodeBuffer = new ByteCodeBuffer(code);
    this.builtins = builtins;
    this.globals = globals;
    this.locals = new PyDictObject();
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
    cells = new PyObject[code.freeVarsSize()];
  }

  public PyFrameObject(
      PyFunctionObject func,
      PyCodeObject code,
      PyDictObject builtins,
      PyDictObject globals,
      PyFrameObject back) {
    assert code != null;
    this.func = func;
    this.code = code;
    byteCodeBuffer = new ByteCodeBuffer(code);
    this.builtins = builtins;
    this.globals = globals;
    this.locals = new PyDictObject();
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
    cells = new PyObject[func.getFreeVarsSize()];
    var funcClosure = (PyTupleObject) func.getFuncClosure();
    for (int i = 0; i < func.getFreeVarsSize(); i++) {
      try {
        cells[i] = funcClosure.get(i);
      } catch (PyException ignore) {
      }
    }
  }

  public PyFrameObject(
      PyCodeObject code,
      PyDictObject builtins,
      PyDictObject globals,
      PyDictObject locals,
      PyFrameObject back) {
    assert code != null;
    this.code = code;
    byteCodeBuffer = new ByteCodeBuffer(code);
    this.builtins = builtins;
    this.globals = globals;
    this.locals = locals;
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
    cells = new PyObject[code.freeVarsSize()];
  }

  public PyFrameObject(
      PyFunctionObject func,
      PyCodeObject code,
      PyDictObject builtins,
      PyDictObject globals,
      PyDictObject locals,
      PyFrameObject back)
      throws PyException {
    assert code != null;
    this.func = func;
    this.code = code;
    byteCodeBuffer = new ByteCodeBuffer(code);
    this.builtins = builtins;
    this.globals = globals;
    this.locals = locals;
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
    cells = new PyObject[func.getFreeVarsSize()];
    var funcClosure = (PyTupleObject) func.getFuncClosure();
    for (int i = 0; i < func.getFreeVarsSize(); i++) {
      cells[i] = funcClosure.get(i);
    }
  }

  @Deprecated
  public PyFrameObject(PyCodeObject code, PyDictObject builtins, PyFrameObject back) {
    this.code = code;
    byteCodeBuffer = new ByteCodeBuffer(code);
    this.builtins = builtins;
    this.locals = new PyDictObject();
    this.globals = new PyDictObject();
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
    cells = new PyObject[code.freeVarsSize()];
  }

  public PyFrameObject(
      PyCodeObject code, PyDictObject builtins, PyDictObject globals, PyDictObject locals) {
    this.code = code;
    byteCodeBuffer = new ByteCodeBuffer(code);
    this.builtins = builtins;
    this.locals = locals;
    this.globals = globals;
    stack = new PyObject[code.getCoStackSize()];
    localPlus = new PyObject[code.getCoNLocals()];
    cells = new PyObject[code.freeVarsSize()];
  }

  public PyFunctionObject getFunc() {
    return func;
  }

  public void setFunc(PyFunctionObject func) {
    this.func = func;
  }

  public PyCodeObject getCode() {
    return code;
  }

  public PyDictObject getBuiltins() {
    return builtins;
  }

  public void setBuiltins(PyDictObject builtins) {
    this.builtins = builtins;
  }

  public PyDictObject getGlobals() {
    return globals;
  }

  public void setGlobals(PyDictObject globals) {
    this.globals = globals;
  }

  public void setFreeVars(int idx, PyObject o) {
    assert cells[idx] instanceof PyCellObject;
    var cell = (PyCellObject) cells[idx];
    cell.setRef(o);
  }

  public int getFreeVarsSize() {
    return cells.length;
  }

  public PyObject getFreeVars(int idx) {
    assert cells[idx] instanceof PyCellObject;
    var cell = (PyCellObject) cells[idx];
    return cell.getRef();
  }

  public PyCellObject getFreeVarsCell(int idx) {
    assert cells[idx] instanceof PyCellObject;
    return (PyCellObject) cells[idx];
  }

  public PyDictObject getLocals() {
    return locals;
  }

  public void setLocals(PyDictObject locals) {
    this.locals = locals;
  }

  public int getUsed() {
    return used;
  }

  public int getStackSize() {
    return used;
  }

  public PyFrameObject getBack() {
    return back;
  }

  public void setBack(PyFrameObject back) {
    this.back = back;
  }

  public boolean isExecuting() {
    return isExecuting;
  }

  public void setExecuting(boolean executing) {
    isExecuting = executing;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  public void push(PyObject o) {
    stack[used++] = o;
  }

  public PyObject pop() {
    return stack[--used];
  }

  public PyObject top() {
    assert used > 0;
    return stack[used - 1];
  }

  /** `used` represent minimum available idx to use */
  public PyObject top(int delta) {
    assert used >= delta && delta >= 1;
    return stack[used - delta];
  }

  public void setTop(int delta, PyObject o) {
    assert delta >= 1;
    stack[used - delta] = o;
  }

  public void setTop(PyObject o) {
    stack[used - 1] = o;
  }

  public boolean hasArgs() {
    return used > 0;
  }

  public PyObject get(int idx) {
    return stack[idx];
  }

  public void decreaseStackPointer(int delta) {
    used -= delta;
  }

  public void increaseStackPointer(int delta) {
    used += delta;
  }

  public PyObject getLocal(int idx) {
    return localPlus[idx];
  }

  public void setLocal(int idx, PyObject o) {
    localPlus[idx] = o;
  }

  public PyUnicodeObject getModuleName() {
    if (func != null) {
      return (PyUnicodeObject) func.getFuncModule();
    }
    return null;
  }

  @Override
  public String toString() {
    return "PyFrameObject{"
        + "stack="
        + Arrays.toString(stack)
        + ", localPlus="
        + Arrays.toString(localPlus)
        + ", builtins="
        + builtins
        + ", globals="
        + globals
        + ", locals="
        + locals
        + '}';
  }

  public ByteCodeBuffer getByteCodeBuffer() {
    return byteCodeBuffer;
  }

  public int addressToLine(int addrQuery) {
    int line = code.getCoFirstLineNo();
    var colnotab = (PyBytesObject) code.getColnotab();
    byte[] data = colnotab.getData();
    int size = data.length / 2;
    int addr = 0;
    int idx = 0;
    while (--size >= 0) {
      addr += data[idx++];
      if (addr > addrQuery) break;
      line += data[idx++];
    }
    return line;
  }

  public void pushTryBlockHandler(TryBlockHandler handler) {
    if (tryBlockHandlerStack == null) tryBlockHandlerStack = new Stack<>();
    tryBlockHandlerStack.push(handler);
  }

  public TryBlockHandler popTryBlockHandler() {
    if (tryBlockHandlerStack == null || tryBlockHandlerStack.size() == 0) return null;
    return tryBlockHandlerStack.pop();
  }

  public TryBlockHandler peekTryBlockHandler() {
    if (tryBlockHandlerStack == null) return null;
    return tryBlockHandlerStack.peek();
  }

  public int getTryBlockSize() {
    if (tryBlockHandlerStack == null) return 0;
    return tryBlockHandlerStack.size();
  }

  public int getLastI() {
    return byteCodeBuffer.getCursor();
  }
}
