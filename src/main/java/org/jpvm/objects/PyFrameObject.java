package org.jpvm.objects;

import org.jpvm.objects.types.PyFrameType;
import org.jpvm.pycParser.PyCodeObject;

public class PyFrameObject extends PyObject {

  public static PyObject type = new PyFrameType();
  private final PyCodeObject code;
  /**
   * value stack
   */
  private final PyObject[] stack;
  private PyDictObject builtins;
  private PyDictObject globals;
  private PyDictObject locals;
  /**
   * shows how many slots of stack have been used
   */
  private int used;

  private boolean isExecuting;

  private final PyFrameObject back;

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
  }

  public PyFrameObject(PyCodeObject code, PyDictObject builtins, PyFrameObject back) {
    this.code = code;
    this.builtins = builtins;
    this.locals = new PyDictObject();
    this.globals = new PyDictObject();
    stack = new PyObject[code.getCoStackSize()];
    this.back = back;
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

  public boolean isExecuting() {
    return isExecuting;
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

  public boolean hasArgs() {
    return used > 0;
  }

  public PyObject get(int idx) {
    return stack[idx];
  }

  public void decreaseStackPointer(int delta) {
    used -= delta;
  }
}
