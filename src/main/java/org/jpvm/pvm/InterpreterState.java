package org.jpvm.pvm;

import org.jpvm.errors.PyException;
import org.jpvm.objects.*;

public class InterpreterState {

  /**
   * modules have been imported
   */
  private final PyDictObject modules;
  private final GILRuntimeState gil;
  private final PyListObject searchPath;
  /**
   * the builtins module
   */
  private PyDictObject builtins;
  private int maxRecursionDepth = 100;

  public InterpreterState(long interval) {
    modules = new PyDictObject();
    gil = new GILRuntimeState(interval);
    searchPath = new PyListObject();
  }

  public InterpreterState(PyDictObject builtins, long interval) {
    this(interval);
    this.builtins = builtins;
  }

  public InterpreterState(PyDictObject builtins) {
    this(5000);
    this.builtins = builtins;
  }

  public PyListObject getSearchPath() {
    return searchPath;
  }

  public void addSearchPath(PyUnicodeObject path){
    searchPath.append(path);
  }

  public void addModule(PyUnicodeObject name, PyModuleObject module){
    try {
      modules.put(name, module);
    } catch (PyException ignored) {
    }
  }

  public PyObject getModule(PyUnicodeObject name) throws PyException {
    return modules.get(name);
  }

  public PyDictObject getBuiltins() {
    return builtins;
  }

  public void setBuiltins(PyDictObject builtins) {
    this.builtins = builtins;
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
