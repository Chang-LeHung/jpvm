package org.jpvm.pvm;

import org.jpvm.objects.PyObject;

public class ThreadState {

  private InterpreterState is;
  private PyObject builtins;

  private int recursionDepth;

  private int gilCounter;

  private PyObject curExcType;
  private PyObject curExcValue;
  private PyObject curExcTrace;


  public ThreadState(InterpreterState is, PyObject builtins) {
    this.is = is;
    this.builtins = builtins;
  }

  public ThreadState() {
  }

  public InterpreterState getIs() {
    return is;
  }

  public void setIs(InterpreterState is) {
    this.is = is;
  }

  public PyObject getBuiltins() {
    return builtins;
  }

  public void setBuiltins(PyObject builtins) {
    this.builtins = builtins;
  }
}
