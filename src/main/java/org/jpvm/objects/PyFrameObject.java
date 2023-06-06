package org.jpvm.objects;

import org.jpvm.objects.types.PyFrameType;
import org.jpvm.pycParser.PyCodeObject;

import java.util.Arrays;

public class PyFrameObject extends PyObject {

  public static PyObject type = new PyFrameType();
  private final PyCodeObject code;
  /**
   * value stack
   */
  private final PyObject[] stack;
  private final PyObject[] localPlus;
  private PyDictObject builtins;
  private PyDictObject globals;
  private PyDictObject locals;
  /**
   * shows how many slots of stack have been used
   */
  private int used;
  private boolean isExecuting;
  private PyFrameObject back;

  public PyFrameObject(PyCodeObject code,
                       PyDictObject builtins,
                       PyDictObject globals, PyFrameObject back) {
    assert code != null;
    this.code = code;
    this.builtins = builtins;
    this.globals = globals;
    this.locals = new PyDictObject();
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
  }

  public PyFrameObject(PyCodeObject code,
                       PyDictObject builtins,
                       PyDictObject globals, PyDictObject locals,
                       PyFrameObject back) {
    assert code != null;
    this.code = code;
    this.builtins = builtins;
    this.globals = globals;
    this.locals = locals;
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
  }

  public PyFrameObject(PyCodeObject code, PyDictObject builtins, PyFrameObject back) {
    this.code = code;
    this.builtins = builtins;
    this.locals = new PyDictObject();
    this.globals = new PyDictObject();
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
    localPlus = new PyObject[code.getCoNLocals()];
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

  public PyDictObject getLocals() {
    return locals;
  }

  public void setLocals(PyDictObject locals) {
    this.locals = locals;
  }

  public int getUsed() {
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
    return stack[used - 1];
  }

  public PyObject top(int delta) {
    assert used >= delta && delta >= 1;
    return stack[used - delta];
  }

  public void setTop(int delta, PyObject o) {
    assert used >= delta && delta >= 1;
    stack[used - delta] = o;
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

  @Override
  public String toString() {
    return "PyFrameObject{" +
        "stack=" + Arrays.toString(stack) +
        ", localPlus=" + Arrays.toString(localPlus) +
        ", builtins=" + builtins +
        ", globals=" + globals +
        ", locals=" + locals +
        '}';
  }
}
