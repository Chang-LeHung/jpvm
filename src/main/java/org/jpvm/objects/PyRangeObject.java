package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyRangeType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.python.BuiltIn;

public class PyRangeObject extends PyObject implements TypeIterable {

  public static PyObject type = new PyRangeType();
  private final int start;
  private final int end;
  private final int step;

  private int cur;

  public PyRangeObject(int start, int end, int step) {
    this.start = start;
    this.end = end;
    this.step = step;
    cur = start;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public int getStep() {
    return step;
  }

  public int getCur() {
    return cur;
  }

  public static class PyRangeItrType extends PyTypeType {
    private final PyUnicodeObject name;

    public PyRangeItrType() {
      name = new PyUnicodeObject("range_iterator");
    }

    @Override
    public PyUnicodeObject getTypeName() {
      return name;
    }

  }

  private class PyRangeItrObject extends PyObject implements TypeDoIterate {

    public static PyObject type = new PyRangeItrType();

    @Override
    public PyObject next() throws PyException {
      if (hasNext()) {
        PyLongObject ret = new PyLongObject(cur);
        cur += step;
        return ret;
      }
      return BuiltIn.PyExcStopIteration;
    }

    @Override
    public boolean hasNext() {
      if (step < 0)
        return (step+cur) >= end;
      else
        return (step+cur) <= end;
    }
  }

  @Override
  public TypeDoIterate getIterator() throws PyNotImplemented {
    return new PyRangeItrObject();
  }

  @Override
  public String toString() {
    return "PyRangeObject{" +
        "start=" + start +
        ", end=" + end +
        ", step=" + step +
        ", cur=" + cur +
        '}';
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return new PyUnicodeObject(toString());
  }
}
