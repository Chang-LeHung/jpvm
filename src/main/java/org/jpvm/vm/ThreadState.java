package org.jpvm.vm;

import org.jpvm.excptions.ExceptionInfo;
import org.jpvm.excptions.PyTraceBackObject;
import org.jpvm.excptions.pyobjs.PyExceptionObject;
import org.jpvm.excptions.types.PyBaseExceptionType;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyFrameObject;
import org.jpvm.objects.PyObject;

public class ThreadState {

  private boolean isMainThread;
  private InterpreterState is;
  private int recursionDepth;

  /** point to stack top exception, exception being handled. */
  private ExceptionInfo exceptionInfo;

  /** {@link PyBaseExceptionType} */
  private PyBaseExceptionType curExcType;

  /** {@link PyTraceBackObject} */
  private PyExceptionObject curExcValue;

  private PyTraceBackObject curExcTrace;

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

  public PyBaseExceptionType getCurExcType() {
    return curExcType;
  }

  public PyTraceBackObject getCurExcTrace() {
    return curExcTrace;
  }

  public void setCurExcTrace(PyTraceBackObject curExcTrace) {
    this.curExcTrace = curExcTrace;
  }

  public ExceptionInfo getExceptionInfo() {
    if (exceptionInfo == null)
      exceptionInfo = new ExceptionInfo();
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

  public void setCurExcType(PyBaseExceptionType curExcType) {
    this.curExcType = curExcType;
  }

  public PyExceptionObject getCurExcValue() {
    return curExcValue;
  }

  public void setCurExcValue(PyExceptionObject curExcValue) {
    this.curExcValue = curExcValue;
  }
}
