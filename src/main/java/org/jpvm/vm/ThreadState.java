package org.jpvm.vm;

import org.jpvm.excptions.ExceptionInfo;
import org.jpvm.excptions.PyExceptionContext;
import org.jpvm.excptions.PyTraceBackObject;
import org.jpvm.excptions.types.PyBaseExceptionType;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;

public class ThreadState {

  private boolean isMainThread;
  private InterpreterState is;
  private int recursionDepth;

  /** point to stack top exception, exception being handled currently. */
  private ExceptionInfo exceptionInfo;

  /** {@link PyBaseExceptionType} */
  private PyObject curExcType;
  /** {@link PyExceptionContext} */
  private PyObject curExcValue;
  /** {@link PyTraceBackObject} */
  private PyObject curExcTrace;

  private PyDictObject builtins;

  private PyFrameObject currentFrame;

  public ThreadState(InterpreterState is) {
    this.is = is;
    builtins = is.getBuiltins();
  }

  public ThreadState() {}

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

  public ExceptionInfo getExceptionInfo() {
    if (exceptionInfo == null) exceptionInfo = new ExceptionInfo();
    return exceptionInfo;
  }

  public void setExceptionInfo(ExceptionInfo exceptionInfo) {
    this.exceptionInfo = exceptionInfo;
  }

  public boolean isMainThread() {
    return isMainThread;
  }

  public void setMainThread(boolean mainThread) {
    isMainThread = mainThread;
  }
}
