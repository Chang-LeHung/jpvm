package org.jpvm.objects;

public class PyExcStopIteration extends PyObject {

  public PyExcStopIteration() {
    super();
  }

  @Override
  public String toString() {
    return "PyExcStopIteration";
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(toString());
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }
}
