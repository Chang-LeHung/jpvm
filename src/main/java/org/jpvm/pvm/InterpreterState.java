package org.jpvm.pvm;

import org.jpvm.errors.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyModuleObject;
import org.jpvm.objects.PyUnicodeObject;

public class InterpreterState {

  /**
   * modules have been imported
   */
  private final PyDictObject modules;

  /**
   * the builtins module
   */
  private PyDictObject builtins;

  private final GILRuntimeState gil;

  private int maxRecursionDepth = 100;

  public InterpreterState(long interval) {
    modules = new PyDictObject();
    gil = new GILRuntimeState(interval);
  }

  public InterpreterState(PyDictObject builtins, long interval) {
    this(interval);
    this.builtins = builtins;
  }

  public InterpreterState(PyDictObject builtins) {
    this(5000);
    this.builtins = builtins;
  }

  public void setBuiltins(PyDictObject builtins) {
    this.builtins = builtins;
  }

  public void addModule(PyUnicodeObject name, PyModuleObject module){
    try {
      modules.put(name, module);
    } catch (PyException ignored) {
    }
  }

  public PyDictObject getBuiltins() {
    return builtins;
  }

  public int getMaxRecursionDepth() {
    return maxRecursionDepth;
  }

  public void setMaxRecursionDepth(int maxRecursionDepth) {
    this.maxRecursionDepth = maxRecursionDepth;
  }

  public void takeGIL() {
    gil.takeGIL();
  }

  public void dropGIL() {
    gil.dropGIL();
  }

  public long getGILInterval() {
    return gil.getInterval();
  }

  public void setGILInterval(long interval) {
    gil.setInterval(interval);
  }

  public boolean isLocked() {
    return gil.isLocked();
  }

  public long getSwitchNumber() {
    return gil.getSwitchNumber();
  }

  public void setSwitchNumber(long switchNumber) {
    gil.setSwitchNumber(switchNumber);
  }

  public Thread getLastHolder() {
    return gil.getLastHolder();
  }

  public boolean isDropGILRequest() {
    return gil.isDropGILRequest();
  }

  public void registerModule(PyUnicodeObject name, PyModuleObject module) throws PyException {
    modules.put(name, module);
  }
}
