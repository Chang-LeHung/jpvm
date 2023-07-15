package org.jpvm.objects;

import org.junit.Test;

public class PyBoolObjectTest {

  @Test
  public void isBool() {
    PyBoolObject o1 = new PyBoolObject(false);
    boolean res = o1.isBool();
    System.out.println(res);
    assert !res;
    System.out.println("\u001B[32m method isBool of PyBoolObject pass\u001B[0m");
  }

  @Test
  public void isTrue() {
    PyBoolObject o1 = new PyBoolObject(true);
    assert o1.isTrue();
    System.out.println("\u001B[32m method isTrue of PyBoolObject pass\u001B[0m");
  }

  @Test
  public void isFalse() {
    PyBoolObject o1 = new PyBoolObject(false);
    assert o1.isFalse();
    System.out.println("\u001B[32m method isFalse of PyBoolObject pass\u001B[0m");
  }

  @Test
  public void and() {
    PyBoolObject o1 = new PyBoolObject(false);
    PyBoolObject o2 = new PyBoolObject(true);
    var res = (PyBoolObject) o1.and(o2);
    assert res.isFalse();
    System.out.println("\u001B[32m method and of PyBoolObject pass\u001B[0m");
  }

  @Test
  public void xor() {
    PyBoolObject o1 = new PyBoolObject(false);
    PyBoolObject o2 = new PyBoolObject(true);
    var res = (PyBoolObject) o1.xor(o2);
    assert !res.isFalse();
    System.out.println("\u001B[32m method xor of PyBoolObject pass\u001B[0m");
  }

  @Test
  public void or() {
    PyBoolObject o1 = new PyBoolObject(false);
    PyBoolObject o2 = new PyBoolObject(true);
    var res = (PyBoolObject) o1.or(o2);
    assert !res.isFalse();
    System.out.println("\u001B[32m method or of PyBoolObject pass\u001B[0m");
  }
}
