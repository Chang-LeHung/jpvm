package org.jpvm.objects;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.internal.NumberHelper;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PySliceType;
import org.jpvm.python.BuiltIn;

public class PySliceObject extends PyObject{

  public static PyObject type = new PySliceType();

  private final PyObject start;
  private final PyObject end;
  private final PyObject step;

  public PySliceObject(PyObject start, PyObject end, PyObject step) {
    this.start = start;
    this.end = end;
    this.step = step;
  }

  @Override
  public Object getType() {
    return type;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return type.getTypeName();
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(String.format("slice(%d, %d, %d,)", start, end, step));
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyBoolObject richCompare(PyObject o) {
    if (!(o instanceof PySliceObject slice)) {
      return BuiltIn.False;
    }
    if (start == slice.start && end == slice.end && step == slice.step)
      return BuiltIn.True;
    return BuiltIn.False;
  }

  @Override
  public PyBoolObject isHashable() {
    return new PyBoolObject(true);
  }

  public PyObject getStart() {
    return start;
  }

  public PyObject getEnd() {
    return end;
  }

  public PyObject getStep() {
    return step;
  }

  public PyListObject unpacked(PyObject o) {
    if (!(o instanceof TypeIterable iter))
      return null;
    PyObject iterator;
    try {
      iterator = iter.getIterator();
    } catch (PyNotImplemented e) {
      throw new RuntimeException(e);
    }
    if (iterator instanceof TypeDoIterate it) {
      int b, e, s;
      Long l;
      if (start == BuiltIn.None)
        b = 0;
      else {
        l = NumberHelper.transformPyObject2Long(start);
        assert l != null;
        b = l.intValue();
      }
      if (end == BuiltIn.None)
        e = it.size();
      else {
        l = NumberHelper.transformPyObject2Long(end);
        assert l != null;
        e = l.intValue();
      }

      if (step == BuiltIn.None)
        s = 1;
      else {
        l = NumberHelper.transformPyObject2Long(step);
        assert l != null;
        s = l.intValue();
      }
      PyListObject list = new PyListObject();
      if (s < 0) {
        for(int i=(e-1+it.size())%it.size(); i>=((b+it.size())%it.size()); i+=s){
          assert i >= 0;
          list.append(new PyLongObject(i));
        }
      }else {
        for(int i=((b+it.size())%it.size()); i<(e+it.size())%it.size(); i+=s){
          assert i >= 0;
          list.append(new PyLongObject(i));
        }
      }
      return list;
    }
    return null;
  }
}
