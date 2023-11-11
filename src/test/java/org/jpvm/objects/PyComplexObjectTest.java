package org.jpvm.objects;

import org.jpvm.excptions.objs.PyException;
import org.junit.Test;

public class PyComplexObjectTest {

  @Test
  public void add() throws PyException {
    PyComplexObject o1 = new PyComplexObject(new PyFloatObject(2), new PyFloatObject(3));
    PyComplexObject o2 = new PyComplexObject(new PyFloatObject(2), new PyFloatObject(3));
    var res = (PyComplexObject) o1.add(o2);
    System.out.println(res.repr());
    assert res.getReal().getData() == 4 && res.getImage().getData() == 6;
    System.out.println("\u001B[32m method add of PyComplexObject pass\u001B[0m");
  }

  @Test
  public void sub() throws PyException {
    PyComplexObject o1 = new PyComplexObject(new PyFloatObject(4), new PyFloatObject(3));
    PyComplexObject o2 = new PyComplexObject(new PyFloatObject(2), new PyFloatObject(5));
    var res = (PyComplexObject) o1.sub(o2);
    System.out.println(res.repr());
    assert res.getReal().getData() == 2 && res.getImage().getData() == -2;
    System.out.println("\u001B[32m method sub of PyComplexObject pass\u001B[0m");
  }

  @Test
  public void mul() throws PyException {
    PyComplexObject o1 = new PyComplexObject(new PyFloatObject(4), new PyFloatObject(3));
    PyComplexObject o2 = new PyComplexObject(new PyFloatObject(2), new PyFloatObject(5));
    var res = (PyComplexObject) o1.mul(o2);
    System.out.println(res.repr());
    assert res.getReal().getData() == -7 && res.getImage().getData() == 26;
    System.out.println("\u001B[32m method mul of PyComplexObject pass\u001B[0m");
  }

  @Test
  public void pow() {}

  @Test
  public void trueDiv() throws PyException {
    PyComplexObject o1 = new PyComplexObject(new PyFloatObject(2), new PyFloatObject(2));
    PyComplexObject o2 = new PyComplexObject(new PyFloatObject(2), new PyFloatObject(-2));
    var res = (PyComplexObject) o1.trueDiv(o2);
    System.out.println(res.repr());
    assert res.getReal().getData() == 0 && res.getImage().getData() == 1;
    System.out.println("\u001B[32m method div of PyComplexObject pass\u001B[0m");
  }
}
