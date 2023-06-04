package org.jpvm.module.sys;

import org.jpvm.module.filesteam.PyFileStreamObject;
import org.jpvm.objects.PyObject;

public class Sys extends PyObject {

  public static PyFileStreamObject stdout = new PyFileStreamObject(true, false, false);
  public static PyFileStreamObject stdin = new PyFileStreamObject(false, true, false);
  public static PyFileStreamObject stderr = new PyFileStreamObject(false, false, true);
}
