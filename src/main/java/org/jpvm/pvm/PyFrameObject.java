package org.jpvm.pvm;

import org.jpvm.objects.PyObject;
import org.jpvm.pycParser.CodeObject;

public class PyFrameObject extends PyObject {

  private final PyFrameObject back;
  private final CodeObject code;
  private final PyObject builtins;
  private final PyObject globals;
  private final PyObject locals;
  /**
   * value stack
   */
  private PyObject[] stack;
  /**
   * shows how many slots of stack have been used
   */
  private int used;

  private boolean isExecuting;


  public PyFrameObject(PyFrameObject back,
                       CodeObject code,
                       PyObject builtins,
                       PyObject globals,
                       PyObject locals) {
    this.back = back;
    this.code = code;
    this.builtins = builtins;
    this.globals = globals;
    this.locals = locals;
    this.stack = new PyObject[code.getCoStackSize()];
  }

  public PyFrameObject getBack() {
    return back;
  }

  public CodeObject getCode() {
    return code;
  }

  public PyObject getBuiltins() {
    return builtins;
  }

  public PyObject getGlobals() {
    return globals;
  }

  public PyObject getLocals() {
    return locals;
  }

  public int getUsed() {
    return used;
  }

  public boolean isExecuting() {
    return isExecuting;
  }
}
