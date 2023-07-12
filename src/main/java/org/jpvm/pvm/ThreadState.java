package org.jpvm.pvm;

import org.jpvm.excptions.PyPythonException;
import org.jpvm.excptions.types.PyPythonBaseExceptionType;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTraceBackObject;

public class ThreadState {

  private InterpreterState is;
  private int recursionDepth;

  private PyPythonBaseExceptionType curExcType;
  private PyPythonException curExcValue;
  private PyTraceBackObject curExcTrace;

  private PyDictObject builtins;

  private PyFrameObject currentFrame;


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

  public PyFrameObject getCurrentFrame() {
    return currentFrame;
  }

  public void setCurrentFrame(PyFrameObject currentFrame) {
    this.currentFrame = currentFrame;
  }

  public PyObject getCurExcType() {
    return curExcType;
  }

  public void setCurExcType(PyPythonBaseExceptionType curExcType) {
    this.curExcType = curExcType;
  }

  public PyObject getCurExcValue() {
    return curExcValue;
  }

  public void setCurExcValue(PyPythonException curExcValue) {
    this.curExcValue = curExcValue;
  }

  public PyObject getCurExcTrace() {
    return curExcTrace;
  }

  public void setCurExcTrace(PyTraceBackObject curExcTrace) {
    this.curExcTrace = curExcTrace;
  }

}
