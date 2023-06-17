package org.jpvm.pvm;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;

public class ThreadState {

  private InterpreterState is;
  private int recursionDepth;

  private PyObject curExcType;
  private PyObject curExcValue;
  private PyObject curExcTrace;

  private PyDictObject builtins;


  public ThreadState(InterpreterState is) {
    this.is = is;
    builtins = is.getBuiltins();
  }

  public ThreadState() {
  }

  public InterpreterState getIs() {
    return is;
  }

  public void setIs(InterpreterState is) {
    this.is = is;
  }

  public PyDictObject getBuiltins() {
    return builtins;
  }

  public void setBuiltins(PyDictObject builtins) {
    this.builtins = builtins;
  }

  public int getRecursionDepth() {
    return recursionDepth;
  }

  public void setRecursionDepth(int recursionDepth) {
    this.recursionDepth = recursionDepth;
  }

  public void increaseRecursionDepth() {
    recursionDepth++;
  }

  public void decreaseRecursionDepth() {
    recursionDepth--;
  }

  public boolean isOverFlow() {
    return recursionDepth > is.getMaxRecursionDepth();
  }

  public PyObject getCurExcType() {
    return curExcType;
  }

  public void setCurExcType(PyObject curExcType) {
    this.curExcType = curExcType;
  }

  public PyObject getCurExcValue() {
    return curExcValue;
  }

  public void setCurExcValue(PyObject curExcValue) {
    this.curExcValue = curExcValue;
  }

  public PyObject getCurExcTrace() {
    return curExcTrace;
  }

  public void setCurExcTrace(PyObject curExcTrace) {
    this.curExcTrace = curExcTrace;
  }

}
