package org.jpvm.objects;

import org.jpvm.objects.types.PyFrameType;
import org.jpvm.pycParser.PyCodeObject;

public class PyFrameObject extends PyObject {

  public static PyObject type = new PyFrameType();
  private final PyFrameObject back;
  private final PyCodeObject code;
  /**
   * value stack
   */
  private final PyObject[] stack;
  private PyObject builtins;
  private PyObject globals;
  private PyObject locals;
  /**
   * shows how many slots of stack have been used
   */
  private int used;

  private boolean isExecuting;


  public PyFrameObject(PyFrameObject back,
                       PyCodeObject code,
                       PyObject builtins,
                       PyObject globals,
                       PyObject locals) {
    assert code != null;
    this.back = back;
    this.code = code;
    this.builtins = builtins;
    this.globals = globals;
    this.locals = locals;
    this.stack = new PyObject[code.getCoStackSize()];
  }

  public static PyBoolObject check(PyObject o) {
    return new PyBoolObject(o == type);
  }

  public PyFrameObject getBack() {
    return back;
  }

  public PyCodeObject getCode() {
    return code;
  }

  public PyObject getBuiltins() {
    return builtins;
  }

  public void setBuiltins(PyObject builtins) {
    this.builtins = builtins;
  }

  public PyObject getGlobals() {
    return globals;
  }

  public void setGlobals(PyObject globals) {
    this.globals = globals;
  }

  public PyObject getLocals() {
    return locals;
  }

  public void setLocals(PyObject locals) {
    this.locals = locals;
  }

  public int getUsed() {
    return used;
  }

  public boolean isExecuting() {
    return isExecuting;
  }

  @Override
  public PyObject getType() {
    return type;
  }
}
