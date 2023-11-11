package org.jpvm.objects;

import org.jpvm.excptions.jobjs.PyException;
import org.junit.Assert;
import org.junit.Test;

public class PyLongObjectTest {

  @Test
  public void testAdd() throws PyException {
    PyLongObject o1 = new PyLongObject(1);
    PyLongObject o2 = new PyLongObject(1);
    var res = (PyLongObject) o1.add(o2);
    System.out.println(res.repr());
    assert res.getData() == 2;
    Assert.assertEquals(res.getData(), 2);
    System.out.println("\u001B[32m method add of PyLongObject pass\u001B[0m");
  }

  @Test
  public void sub() throws PyException {
    PyLongObject o1 = new PyLongObject(1);
    PyLongObject o2 = new PyLongObject(2);
    var res = (PyLongObject) o2.sub(o1);
    System.out.println(res.repr());
    assert res.getData() == 1;
    //        Assert.assertEquals(res.getData(), 1);
    System.out.println("\u001B[32m method sub of PyLongObject pass\u001B[0m");
  }

  @Test
  public void mul() throws PyException {
    PyLongObject o1 = new PyLongObject(2);
    PyLongObject o2 = new PyLongObject(3);
    var res = (PyLongObject) o1.mul(o2);
    System.out.println(res.repr());
    assert res.getData() == 6;
    System.out.println("\u001B[32m method mul of PyLongObject pass\u001B[0m");
  }

  @Test
  public void mod() throws PyException {
    PyLongObject o1 = new PyLongObject(2);
    PyLongObject o2 = new PyLongObject(3);
    var res = (PyLongObject) o2.mod(o1);
    System.out.println(res.repr());
    assert res.getData() == 1;
    System.out.println("\u001B[32m method mod of PyLongObject pass\u001B[0m");
  }

  @Test
  public void divmod() throws PyException {
    PyLongObject o1 = new PyLongObject(4);
    PyLongObject o2 = new PyLongObject(10);
    var res = (PyTupleObject) o2.divmod(o1);
    System.out.println(res);
    assert res.get(0).equals(2.5);
    assert res.get(1).equals(2);
    System.out.println("\u001B[32m method divmod of PyLongObject pass\u001B[0m");
  }

  @Test
  public void pow() throws PyException {
    PyLongObject o1 = new PyLongObject(2);
    PyLongObject o2 = new PyLongObject(3);
    var res = (PyLongObject) o2.pow(o1);
    System.out.println(res.repr());
    assert res.getData() == 9;
    System.out.println("\u001B[32m method pow of PyLongObject pass\u001B[0m");
  }

  @Test
  public void neg() {
    PyLongObject o1 = new PyLongObject(1);
    var res = (PyLongObject) o1.neg();
    System.out.println(res.repr());
    assert res.getData() == -1;
    System.out.println("\u001B[32m method neg of PyLongObject pass\u001B[0m");
  }

  @Test
  public void pos() {
    PyLongObject o1 = new PyLongObject(1);
    var res = (PyLongObject) o1.pos();
    System.out.println(res.repr());
    assert res.getData() == 1;
    System.out.println("\u001B[32m method pos of PyLongObject pass\u001B[0m");
  }

  @Test
  public void abs() {
    PyLongObject o1 = new PyLongObject(-1);
    var res = (PyLongObject) o1.abs();
    System.out.println(res.repr());
    assert res.getData() == 1;
    System.out.println("\u001B[32m method abs of PyLongObject pass\u001B[0m");
  }

  @Test
  public void bool() {
    PyLongObject o1 = new PyLongObject(10);
    var res = (PyBoolObject) o1.bool();
    System.out.println(res);
    assert res.isTrue();
    System.out.println("\u001B[32m method bool of PyLongObject pass\u001B[0m");
  }

  @Test
  public void invert() {
    PyLongObject o1 = new PyLongObject(1);
    var res = (PyLongObject) o1.invert();
    System.out.println(res.repr());
    assert res.getData() == -2;
    System.out.println("\u001B[32m method invert of PyLongObject pass\u001B[0m");
  }

  @Test
  public void lshift() throws PyException {
    PyLongObject o1 = new PyLongObject(1);
    PyLongObject o2 = new PyLongObject(2);
    var res = (PyLongObject) o1.lshift(o2);
    System.out.println(res.repr());
    assert res.getData() == 4;
    System.out.println("\u001B[32m method lshift of PyLongObject pass\u001B[0m");
  }

  @Test
  public void rshift() throws PyException {
    PyLongObject o1 = new PyLongObject(4);
    PyLongObject o2 = new PyLongObject(1);
    var res = (PyLongObject) o1.rshift(o2);
    System.out.println(res.repr());
    assert res.getData() == 2;
    System.out.println("\u001B[32m method rshift of PyLongObject pass\u001B[0m");
  }

  @Test
  public void and() throws PyException {
    PyLongObject o1 = new PyLongObject(1);
    PyLongObject o2 = new PyLongObject(2);
    var res = (PyLongObject) o1.and(o2);
    System.out.println(res.repr());
    assert res.getData() == 0;
    System.out.println("\u001B[32m method and of PyLongObject pass\u001B[0m");
  }

  @Test
  public void xor() throws PyException {
    PyLongObject o1 = new PyLongObject(5);
    PyLongObject o2 = new PyLongObject(3);
    var res = (PyLongObject) o1.xor(o2);
    System.out.println(res.repr());
    assert res.getData() == 6;
    System.out.println("\u001B[32m method xor of PyLongObject pass\u001B[0m");
  }

  @Test
  public void or() throws PyException {
    PyLongObject o1 = new PyLongObject(4);
    PyLongObject o2 = new PyLongObject(3);
    var res = (PyLongObject) o1.or(o2);
    System.out.println(res.repr());
    assert res.getData() == 7;
    System.out.println("\u001B[32m method or of PyLongObject pass\u001B[0m");
  }

  @Test
  public void nbInt() {
    PyLongObject o1 = new PyLongObject(4);
    var res = (PyLongObject) o1.nbInt();
    System.out.println(res.repr());
    assert res.getData() == 4;
    System.out.println("\u001B[32m method nbInt of PyLongObject pass\u001B[0m");
  }

  @Test
  public void nbFloat() {
    PyLongObject o1 = new PyLongObject(4);
    var res = (PyFloatObject) o1.nbFloat();
    System.out.println(res.repr());
    assert res.getData() == 4.0;
    System.out.println("\u001B[32m method nbFloat of PyLongObject pass\u001B[0m");
  }

  @Test
  public void floorDiv() throws PyException {
    PyLongObject o1 = new PyLongObject(4);
    PyLongObject o2 = new PyLongObject(3);
    var res = (PyLongObject) o1.floorDiv(o2);
    System.out.println(res.repr());
    assert res.getData() == 1;
    System.out.println("\u001B[32m method floorDiv of PyLongObject pass\u001B[0m");
  }

  @Test
  public void trueDiv() throws PyException {
    PyLongObject o1 = new PyLongObject(10);
    PyLongObject o2 = new PyLongObject(4);
    var res = (PyFloatObject) o1.trueDiv(o2);
    System.out.println(res.repr());
    assert res.getData() == 2.5;
    System.out.println("\u001B[32m method trueDiv of PyLongObject pass\u001B[0m");
  }

  @Test
  public void index() {
    PyLongObject o1 = new PyLongObject(4);
    var res = (PyLongObject) o1.index();
    System.out.println(res.repr());
    assert res.getData() == 4;
    System.out.println("\u001B[32m method index of PyLongObject pass\u001B[0m");
  }

  @Test
  public void testEquals() {
    PyLongObject o1 = new PyLongObject(10);
    PyLongObject o2 = new PyLongObject(4);
    var res = o1.equals(o2);
    System.out.println(res);
    assert !res;
    System.out.println("\u001B[32m method equals of PyLongObject pass\u001B[0m");
  }
}
