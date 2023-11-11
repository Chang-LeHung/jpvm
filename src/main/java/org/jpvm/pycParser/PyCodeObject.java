package org.jpvm.pycParser;

import org.jpvm.excptions.jobjs.PyException;
import org.jpvm.module.Marshal;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyCodeObject extends PyObject {

  /** non-KwOnly argument count */
  private int coArgument;
  /** KwOnly argument count */
  private int coKwOnlyArCnt;
  /** Positional only argument count */
  private int coPosOnlyArCnt;

  private int coNLocals;
  private int coStackSize;
  private int coFlags;
  private int coFirstLineNo;
  /** type of {@link org.jpvm.objects.PyBytesObject} */
  private PyObject coCode;
  /** type of {@link org.jpvm.objects.PyTupleObject} */
  private PyObject coConsts;
  /** type of {@link org.jpvm.objects.PyTupleObject} */
  private PyObject coNames;
  /** type of {@link org.jpvm.objects.PyTupleObject} */
  private PyObject coVarNames;
  /** type of {@link org.jpvm.objects.PyTupleObject} */
  private PyObject coFreeVars;
  /** type of {@link org.jpvm.objects.PyTupleObject} */
  private PyObject coCellVars;
  /** type of {@link org.jpvm.objects.PyUnicodeObject} */
  private PyObject coFileName;
  /** type of {@link org.jpvm.objects.PyUnicodeObject} */
  private PyObject coName;
  /** type of {@link org.jpvm.objects.PyBytesObject} */
  private PyObject colnotab;

  private PyObject coZombieFrame;
  private PyObject coWeakRefList;
  private PyObject coExtra;

  private String parentDir;

  public int getCoArgument() {
    return coArgument;
  }

  public void setCoArgument(int coArgument) {
    this.coArgument = coArgument;
  }

  public int getCoKwOnlyArCnt() {
    return coKwOnlyArCnt;
  }

  public void setCoKwOnlyArCnt(int coKwOnlyArCnt) {
    this.coKwOnlyArCnt = coKwOnlyArCnt;
  }

  public PyObject getCoVarNames() {
    return coVarNames;
  }

  public void setCoVarNames(PyObject coVarNames) {
    this.coVarNames = coVarNames;
  }

  public PyObject getCoConsts() {
    return coConsts;
  }

  public void setCoConsts(PyObject coConsts) {
    this.coConsts = coConsts;
  }

  public int getCoNLocals() {
    return coNLocals;
  }

  public void setCoNLocals(int coNLocals) {
    this.coNLocals = coNLocals;
  }

  public int getCoPosOnlyArCnt() {
    return coPosOnlyArCnt;
  }

  public void setCoPosOnlyArCnt(int coPosOnlyArCnt) {
    this.coPosOnlyArCnt = coPosOnlyArCnt;
  }

  public int getCoStackSize() {
    return coStackSize;
  }

  public void setCoStackSize(int coStackSize) {
    this.coStackSize = coStackSize;
  }

  public int getCoFlags() {
    return coFlags;
  }

  public void setCoFlags(int coFlags) {
    this.coFlags = coFlags;
  }

  public int getCoFirstLineNo() {
    return coFirstLineNo;
  }

  public void setCoFirstLineNo(int coFirstLineNo) {
    this.coFirstLineNo = coFirstLineNo;
  }

  public PyObject getCoCode() {
    return coCode;
  }

  public void setCoCode(PyObject coCode) {
    this.coCode = coCode;
  }

  public PyObject getCoNames() {
    return coNames;
  }

  public void setCoNames(PyObject coNames) {
    this.coNames = coNames;
  }

  public PyObject getCoFreeVars() {
    return coFreeVars;
  }

  public void setCoFreeVars(PyObject coFreeVars) {
    this.coFreeVars = coFreeVars;
  }

  public PyObject getCoCellVars() {
    return coCellVars;
  }

  public void setCoCellVars(PyObject coCellVars) {
    this.coCellVars = coCellVars;
  }

  public PyObject getCoFileName() {
    return coFileName;
  }

  public void setCoFileName(PyObject coFileName) {
    this.coFileName = coFileName;
  }

  public PyObject getCoName() {
    return coName;
  }

  public void setCoName(PyObject coName) {
    this.coName = coName;
  }

  public PyObject getColnotab() {
    return colnotab;
  }

  public void setColnotab(PyObject colnotab) {
    this.colnotab = colnotab;
  }

  public PyObject getCoZombieFrame() {
    return coZombieFrame;
  }

  public void setCoZombieFrame(PyObject coZombieFrame) {
    this.coZombieFrame = coZombieFrame;
  }

  public PyObject getCoWeakRefList() {
    return coWeakRefList;
  }

  public void setCoWeakRefList(PyObject coWeakRefList) {
    this.coWeakRefList = coWeakRefList;
  }

  public PyObject getCoExtra() {
    return coExtra;
  }

  public void setCoExtra(PyObject coExtra) {
    this.coExtra = coExtra;
  }

  @Override
  public String toString() {
    return "CodeObject{"
        + "coArgument="
        + coArgument
        + ", coKwOnlyArCnt="
        + coKwOnlyArCnt
        + ", coPosOnlyArCnt="
        + coPosOnlyArCnt
        + ", coNLocals="
        + coNLocals
        + ", coStackSize="
        + coStackSize
        + ", coFlags="
        + coFlags
        + ", coFirstLineNo="
        + coFirstLineNo
        + ", coCode="
        + coCode
        + ", coConsts="
        + coConsts
        + ", coNames="
        + coNames
        + ", coVarNames="
        + coVarNames
        + ", coFreeVars="
        + coFreeVars
        + ", coCellVars="
        + coCellVars
        + ", coFileName="
        + coFileName
        + ", coName="
        + coName
        + ", colnotab="
        + colnotab
        + ", coZombieFrame="
        + coZombieFrame
        + ", coWeakRefList="
        + coWeakRefList
        + ", coExtra="
        + coExtra
        + '}';
  }

  public int freeVarsSize() {
    int c = 0;
    int f = 0;
    if (coFreeVars != null) {
      f = ((PyTupleObject) coFreeVars).size();
    }
    if (coCellVars != null) {
      c = ((PyTupleObject) coCellVars).size();
    }
    return c + f;
  }

  public boolean isGenerator() {
    return (coFlags & Marshal.CO_GENERATOR) != 0;
  }

  public String getParentDir() {
    return parentDir;
  }

  public void setParentDir(String parentDir) {
    this.parentDir = parentDir;
  }

  public void updateFileName(String absFileName) {
    coFileName = new PyUnicodeObject(absFileName);
    var consts = (PyTupleObject) coConsts;
    for (int i = 0; i < consts.size(); i++) {
      try {
        PyObject object = consts.get(i);
        if (object instanceof PyCodeObject code) {
          code.setCoFileName(coFileName);
        }
      } catch (PyException ignore) {
      }
    }
  }
}
