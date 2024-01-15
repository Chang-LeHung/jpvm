package org.jpvm;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.PyListObject;
import org.jpvm.objects.PyLongObject;
import org.jpvm.objects.PySliceObject;
import org.jpvm.objects.PyTupleObject;
import org.junit.Test;

public class TestPySliceObject {

  @Test
  public void test01() throws PyException {
    var start = PyLongObject.getLongObject(5);
    var end = PyLongObject.getLongObject(1);
    var step = PyLongObject.getLongObject(-2);
    PyTupleObject object = new PyTupleObject(10);
    PySliceObject sliceObject = new PySliceObject(start, end, step);
    PyListObject pyListObject = sliceObject.unpacked(object);
    System.out.println(pyListObject.repr());
  }

  @Test
  public void test02() throws PyException {
    var start = PyLongObject.getLongObject(5);
    var end = PyLongObject.getLongObject(1);
    var step = PyLongObject.getLongObject(-1);
    PyTupleObject object = new PyTupleObject(10);
    PySliceObject sliceObject = new PySliceObject(start, end, step);
    PyListObject pyListObject = sliceObject.unpacked(object);
    System.out.println(pyListObject.repr());
  }
}
