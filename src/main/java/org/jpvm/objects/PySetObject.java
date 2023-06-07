package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.pyinterface.TypeName;
import org.jpvm.objects.types.PySetType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.protocols.PySequenceMethods;
import org.jpvm.python.BuiltIn;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PySetObject extends PyObject implements TypeIterable,
    PyNumberMethods, PySequenceMethods {

  public static PyObject type = new PySetType();

  private final Set<PyObject> set;

  private boolean isFrozen;

  public PySetObject() {
    this.set = new HashSet<>();
  }

  public PySetObject(boolean isFrozen) {
    this();
    this.isFrozen = isFrozen;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{");
    for (PyObject object : set) {
      builder.append(object.toString());
      builder.append(", ");
    }
    if (builder.length() > 2)
      builder.delete(builder.length() - 2, builder.length());
    builder.append("}");
    return builder.toString();
  }

  public boolean isFrozen() {
    return isFrozen;
  }

  public void setFrozen(boolean frozen) {
    isFrozen = frozen;
  }

  public void put(PyObject key) throws PyException {
    try {
      set.add(key);
    }catch (ConcurrentModificationException e) {
      throw new PyException("can not change set while  iterating");
    }
  }

  public void putAll(PySetObject key) throws PyException {
    try {
      set.addAll(key.set);
    }catch (ConcurrentModificationException e) {
      throw new PyException("can not change set while  iterating");
    }
  }

  public boolean contains(PyObject key) {
    return set.contains(key);
  }

  @PyClassMethod
  public PyObject add(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      set.add(args.get(0));
      return BuiltIn.None;
    }
    throw new PyException("set method add only require one argument");
  }

  @PyClassMethod
  public PyObject pop(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (set.size() == 0)
      return BuiltIn.None;
    Iterator<PyObject> iterator = set.iterator();
    PyObject next = iterator.next();
    iterator.remove();
    return next;
  }

  @PyClassMethod
  public PyObject remove(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      set.remove(args.get(0));
      return BuiltIn.None;
    }
    throw new PyException("set method remove only require one argument");
  }

  @PyClassMethod
  public PyObject copy(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    PySetObject result = new PySetObject();
    result.set.addAll(set);
    return result;
  }

  @PyClassMethod
  public PyObject clear(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    set.clear();
    return BuiltIn.None;
  }

  @PyClassMethod
  public PyObject issubset(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        if (o.set.containsAll(set))
          return BuiltIn.True;
        else
          return BuiltIn.False;
      }
    }
    throw new PyException("set method issubset only require one PySetObject argument");
  }

  @PyClassMethod
  public PyObject issuperset(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        if (set.containsAll(o.set))
          return BuiltIn.True;
        else
          return BuiltIn.False;
      }
    }
    throw new PyException("set method issubset only require one PySetObject argument");
  }

  @PyClassMethod
  public PyObject intersection(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        PySetObject result = new PySetObject();
        result.set.addAll(set);
        result.set.retainAll(o.set);
        return result;
      }
    }
    throw new PyException("set method intersection only require one PySetObject argument");
  }

  @PyClassMethod
  public PyObject intersection_update(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        set.retainAll(o.set);
        return BuiltIn.None;
      }
    }
    throw new PyException("set method intersection_update only require one PySetObject argument");
  }

  @PyClassMethod
  public PyObject difference(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        PySetObject result = new PySetObject();
        result.set.addAll(set);
        result.set.removeAll(o.set);
        return result;
      }
    }
    throw new PyException("set method difference only require one PySetObject argument");
  }

  @PyClassMethod
  public PyObject difference_update(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        PySetObject result = new PySetObject();
        set.removeAll(o.set);
        return BuiltIn.None;
      }
    }
    throw new PyException("set method difference_update only require one PySetObject argument");
  }

  @PyClassMethod
  public PyObject isdisjoint(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      PyObject object = args.get(0);
      if (object instanceof PySetObject o) {
        PySetObject result = new PySetObject();
        result.set.addAll(o.set);
        result.set.retainAll(set);
        if (result.size() == 0)
          return BuiltIn.True;
        return BuiltIn.False;
      }
    }
    throw new PyException("set method isdisjoint only require one PySetObject argument");
  }

  public int size() {
    return set.size();
  }


  @Override
  public Object toJavaType() {
    return set;
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public TypeDoIterate getIterator() {
    return new PySetItrObject();
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
    return str();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
    if (op == Operator.Py_EQ) {
      if (o instanceof PySetObject obj) {
        if (set.equals(obj.toJavaType()))
          return BuiltIn.True;
        return BuiltIn.False;
      }
      return BuiltIn.False;
    }
    throw new PyUnsupportedOperator("not support operator " + op);
  }

  @Override
  public PyObject sub(PyObject o) throws PyException {
    if (o instanceof PySetObject s) {
      PySetObject ret = new PySetObject();
      ret.putAll(this);
      ret.set.removeAll((Set) s.toJavaType());
      return ret;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject and(PyObject o) throws PyException {
    if (o instanceof PySetObject s) {
      PySetObject ret = new PySetObject();
      ret.putAll(this);
      ret.set.retainAll((Set) s.toJavaType());
      return ret;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject xor(PyObject o) throws PyException {
    if (o instanceof PySetObject s) {
      PySetObject ret = new PySetObject();
      for (PyObject pyObject : set) {
        if (!s.contains(pyObject))
          ret.put(pyObject);
      }
      for (PyObject pyObject : s.set) {
        if (!set.contains(pyObject))
          ret.put(pyObject);
      }
      return ret;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject or(PyObject o) throws PyException {
    if (o instanceof PySetObject s) {
      PySetObject ret = new PySetObject();
      ret.putAll(this);
      ret.set.addAll((Set) s.toJavaType());
      return ret;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject inplaceAnd(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (o instanceof PySetObject s) {
      set.retainAll(s.set);
      return this;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject inplaceXor(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (o instanceof PySetObject s) {
      HashSet<PyObject> set = new HashSet<>();
      for (PyObject pyObject : this.set) {
        if (!s.contains(pyObject))
          set.add(pyObject);
      }
      for (PyObject pyObject : s.set) {
        if (!this.set.contains(pyObject))
          set.add(pyObject);
      }
      this.set.clear();
      this.set.addAll(set);
      return this;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject inplaceOr(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    if (o instanceof PySetObject s) {
      set.addAll((Set) s.toJavaType());
      return this;
    }
    throw new PyTypeNotMatch("set sub function require type set");
  }

  @Override
  public PyObject sqLength(PyObject o) throws PyNotImplemented {
    return new PyLongObject(set.size());
  }

  @Override
  public PyObject sqContain(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
    return new PyBoolObject(set.contains(o));
  }

  public static class PySetItrType extends PyTypeType implements TypeName {

    public PySetItrType() {
      name = "set_iterator";
    }

  }

  public class PySetItrObject extends PyObject implements TypeDoIterate {
    Iterator<PyObject> iterator;

    public PySetItrObject() {
      iterator = set.iterator();
    }

    @Override
    public PyObject next() {
      if (iterator.hasNext())
        return iterator.next();
      return BuiltIn.PyExcStopIteration;
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }
  }
}
