package org.jpvm.objects;

import org.jpvm.errors.PyTypeNotMatch;
import org.junit.Assert;
import org.junit.Test;

public class PyLongObjectTest {

    @Test
    public void testAdd() throws PyTypeNotMatch {
        PyLongObject o1 = new PyLongObject(1);
        PyLongObject o2 = new PyLongObject(1);
        var res = (PyLongObject)o1.add(o2);
        System.out.println(res.repr());
        assert res.getData() == 2;
        Assert.assertEquals(res.getData(), 2);
        System.out.println("\u001B[32m method add of PyLongObject pass\u001B[32m");
    }

    @Test
    public void sub() throws PyTypeNotMatch {
        PyLongObject o1 = new PyLongObject(1);
        PyLongObject o2 = new PyLongObject(2);
        var res = (PyLongObject)o2.sub(o1);
        System.out.println(res.repr());
        assert res.getData() == 1;
//        Assert.assertEquals(res.getData(), 1);
        System.out.println("\u001B[32m method sub of PyLongObject pass\u001B[32m");
    }

    @Test
    public void mul() throws PyTypeNotMatch {
        PyLongObject o1 = new PyLongObject(2);
        PyLongObject o2 = new PyLongObject(3);
        var res = (PyLongObject)o1.mul(o2);
        System.out.println(res.repr());
        assert res.getData() == 6;
        System.out.println("\u001B[32m method mul of PyLongObject pass\u001B[32m");
    }

    @Test
    public void mod() throws PyTypeNotMatch {
        PyLongObject o1 = new PyLongObject(2);
        PyLongObject o2 = new PyLongObject(3);
        var res = (PyLongObject)o2.mod(o1);
        System.out.println(res.repr());
        assert res.getData() == 1;
        System.out.println("\u001B[32m method mod of PyLongObject pass\u001B[32m");
    }

    @Test
    public void divmod() throws PyTypeNotMatch {
        PyLongObject o1 = new PyLongObject(4);
        PyLongObject o2 = new PyLongObject(10);
        var res = (PyTupleObject)o2.divmod(o1);
        System.out.println(res);
        assert res.get(0).equals(2.5);
        assert res.get(1).equals(2);
        System.out.println("\u001B[32m method divmod of PyLongObject pass\u001B[32m");
    }

    @Test
    public void pow() throws PyTypeNotMatch {
        PyLongObject o1 = new PyLongObject(2);
        PyLongObject o2 = new PyLongObject(3);
        var res = (PyLongObject)o2.pow(o1);
        System.out.println(res.repr());
        assert res.getData() == 9;
        System.out.println("\u001B[32m method pow of PyLongObject pass\u001B[32m");
    }

    @Test
    public void neg() {
        PyLongObject o1 = new PyLongObject(1);
        var res = (PyLongObject)o1.neg();
        System.out.println(res.repr());
        assert res.getData() == -1;
        System.out.println("\u001B[32m method neg of PyLongObject pass\u001B[32m");
    }

    @Test
    public void pos() {
        PyLongObject o1 = new PyLongObject(1);
        var res = (PyLongObject)o1.pos();
        System.out.println(res.repr());
        assert res.getData() == 1;
        System.out.println("\u001B[32m method pos of PyLongObject pass\u001B[32m");
    }

    @Test
    public void abs() {
        PyLongObject o1 = new PyLongObject(-1);
        var res = (PyLongObject)o1.abs();
        System.out.println(res.repr());
        assert res.getData() == 1;
        System.out.println("\u001B[32m method abs of PyLongObject pass\u001B[32m");
    }

    @Test
    public void bool() {
        PyLongObject o1 = new PyLongObject(10);
        var res = (PyBoolObject)o1.bool();
        System.out.println(res);
        assert res.isTrue();
        System.out.println("\u001B[32m method bool of PyLongObject pass\u001B[32m");
    }

    @Test
    public void invert() {
        PyLongObject o1 = new PyLongObject(1);
        var res = (PyLongObject)o1.invert();
        System.out.println(res.repr());
        assert res.getData() == -2;
        System.out.println("\u001B[32m method invert of PyLongObject pass\u001B[32m");
    }

    @Test
    public void lshift() {
    }

    @Test
    public void rshift() {
    }

    @Test
    public void and() {
    }

    @Test
    public void xor() {
    }

    @Test
    public void or() {
    }

    @Test
    public void nbInt() {
    }

    @Test
    public void nbFloat() {
    }

    @Test
    public void floorDiv() {
    }

    @Test
    public void trueDiv() {
    }

    @Test
    public void index() {
    }

    @Test
    public void testEquals() {
    }
}
