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
}
