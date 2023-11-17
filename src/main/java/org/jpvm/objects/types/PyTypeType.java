package org.jpvm.objects.types;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.exceptions.jobjs.PyUnsupportedOperator;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDescriptorGet;
import org.jpvm.objects.pyinterface.TypeDescriptorSet;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.vm.MRO;
import org.jpvm.python.BuiltIn;

/** all subclass must override method getType {@link org.jpvm.objects.pyinterface.TypeCheck} */
public class PyTypeType extends PyObject {

  public static final PyTypeType type = new PyTypeType(PyObject.class);

  protected List<PyObject> mro;
  protected PyListObject _mro;
  protected List<PyObject> bases;
  @PyClassAttribute protected PyTupleObject __bases__;

  /**
   * used in super to call clazz's method this is the {@code Class} of class object of this class
   * such as {@code clazz} in {@link PyLongType} is {@code PyLongObject.class}
   */
  protected Class<?> clazz;

  protected String name;
  private boolean typeReady;

  public PyTypeType(Class<?> clazz) {
    this.clazz = clazz;
    name = "type";
    // use List just to avoid ExceptionInInitializerError
    mro = new ArrayList<>();
    bases = new LinkedList<>();
    if (PyObject.type != null) bases.add(PyObject.type); // add base object to bases
  }

  public static PyTypeType getInstance() {
    return type;
  }

  public static PyTupleObject ensureBaseObjectTypeInBases(PyTupleObject bases) throws PyException {
    for (int i = 0; i < bases.size(); i++) {
      if (bases.get(i) == PyObject.type) return bases;
    }
    PyTupleObject res = new PyTupleObject(bases.size() + 1);
    for (int i = 0; i < bases.size(); i++) res.set(i, bases.get(i));
    res.set(bases.size(), PyObject.type);
    return res;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public synchronized void addBase(PyObject base) {
    for (PyObject basis : bases) {
      if (basis == base) return;
    }
    bases.add(base);
  }

  public void addBase(int idx, PyObject base) {
    for (PyObject basis : bases) {
      if (basis == base) return;
    }
    bases.add(idx, base);
  }

  /** this object is subtype of r or not */
  public PyBoolObject isSubType(PyObject r) throws PyException {
    if (!typeReady) getMro();
    if (mro.stream().anyMatch(pyObject -> pyObject == r)) {
      return BuiltIn.True;
    }
    return BuiltIn.False;
  }

  /** lazy loading */
  public synchronized PyListObject getMro() throws PyException {
    if (!typeReady) {
      if (_mro != null) return _mro;
      typeReady = true;
      mro = MRO.mro(this);
      PyListObject object = new PyListObject();
      for (PyObject pyObject : mro) {
        object.add(pyObject);
      }
      _mro = object;
      return object;
    } else return _mro;
  }

  public void setMro(List<PyObject> mro) {
    this.mro = mro;
  }

  @PyClassMethod
  public PyObject mro(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return getMro();
  }

  @PyClassMethod
  public PyObject __mro__(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    return getMro();
  }

  public List<PyObject> getBases() {
    return bases;
  }

  public void setBases(List<PyObject> bases) {
    this.bases = bases;
  }

  public PyTupleObject getBasesClass() {
    if (__bases__ != null) {
      return __bases__;
    }
    PyTupleObject object = new PyTupleObject(bases.size());
    for (int i = 0; i < bases.size(); i++) {
      object.set(i, bases.get(i));
    }
    __bases__ = object;
    return object;
  }

  public void set_mro(PyListObject _mro) {
    this._mro = _mro;
  }

  public void set__bases__(PyTupleObject __bases__) {
    this.__bases__ = __bases__;
  }

  @Override
  public PyUnicodeObject getTypeName() {
    return PyUnicodeObject.getOrCreateFromInternStringPool(name, true);
  }

  @Override
  public PyObject getType() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public PyUnicodeObject str() {
    return new PyUnicodeObject(String.format("<class '%s'>", name));
  }

  @Override
  public PyUnicodeObject repr() {
    return str();
  }

  @Override
  public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
    if (Operator.Py_EQ == op) {
      if (this == o) return BuiltIn.True;
      else return BuiltIn.False;
    }
    throw new PyUnsupportedOperator(getTypeName() + " not support operator " + op);
  }

  @Override
  public PyObject call(PyTupleObject args, PyDictObject kwArgs) throws PyException {
    if (args.size() == 1) {
      String res = "<class '" + getType().getTypeName() + "' >";
      return new PyUnicodeObject(res);
    }
    if (args.size() < 3) throw new PyException(getTypeName() + " require at least 3 arguments");
    PyObject name = args.get(0);
    PyObject bases = args.get(1);
    PyObject dict = args.get(2);
    if (name == null)
      name = kwArgs.get(PyUnicodeObject.getOrCreateFromInternStringPool("name", true));
    if (bases == null)
      bases = kwArgs.get(PyUnicodeObject.getOrCreateFromInternStringPool("bases", true));
    if (dict == null)
      dict = kwArgs.get(PyUnicodeObject.getOrCreateFromInternStringPool("dict", true));
    if (name instanceof PyUnicodeObject n
        && bases instanceof TypeIterable
        && dict instanceof PyDictObject d) {
      PyTupleObject base = PyTupleObject.getTupleFromIterator(bases);
      PyPythonType res = new PyPythonType(n, null, d);
      base = ensureBaseObjectTypeInBases(base);
      List<PyObject> bs = res.getBases();
      bs.clear(); // clean bs see `public PyTypeType(Class<?> clazz)`
      for (int i = 0; i < base.size(); i++) bs.add(base.get(i));
      return res;
    }
    return PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError,
        "type() requires 3 arguments: name str, tuple or list of base classes, dict of attributes");
  }

  @Override
  protected PyObject lookUpType(PyObject key) throws PyException {
    PyObject res;
    PyListObject mro = getMro();
    for (int i = 0; i < mro.size(); i++) {
      PyObject object = mro.get(i);
      if (object != PyTypeType.type && object != this) {
        res = object.getAttr(key);
        if (res != null) return res;
      }
    }
    return null;
  }

  @Override
  public PyObject getAttrInternal(PyObject key) throws PyException {
    PyObject descr = lookUpType(key);
    if (descr instanceof TypeDescriptorGet get && descr instanceof TypeDescriptorSet)
      return get.descrGet(this, getType());
    PyObject object = null;
    if (dict != null) {
      object = dict.get(key);
    }
    var name = (PyUnicodeObject) key;
    if (object == null) {
      try {
        Method method = this.getClass().getMethod(name.getData(), PyObject.parameterTypes);
        if (method.isAnnotationPresent(PyClassMethod.class))
          object = new PyMethodObject(this, method, name.getData());
      } catch (NoSuchMethodException ignore) {
      }
    }
    if (object == null) {
      object = Utils.loadFiled(this, name);
    }
    if (object == null) {
      try {
        Method method =
            this.getClass().getSuperclass().getMethod(name.getData(), PyObject.parameterTypes);
        if (method.isAnnotationPresent(PyClassMethod.class))
          object = new PyMethodObject(this, method, name.getData());
      } catch (NoSuchMethodException ignore) {
      }
    }
    if (object == null) {
      Class<?> clazz = this.getClass().getSuperclass();
      try {
        Field field = clazz.getDeclaredField(name.getData());
        if (field.isAnnotationPresent(PyClassAttribute.class)) {
          field.setAccessible(true);
          Object o = field.get(this);
          return (PyObject) o;
        }
      } catch (NoSuchFieldException | IllegalAccessException ignore) {
      }
    }
    if (object == null) {
      try {
        Method method = clazz.getMethod(name.getData(), PyObject.parameterTypes);
        if (method.isAnnotationPresent(PyClassMethod.class))
          object = new PyMethodObject(method, name.getData());
      } catch (NoSuchMethodException ignore) {
      }
    }
    // PyFunctionObject take priority over PyMethodObject
    if (descr instanceof PyFunctionObject func && object instanceof PyMethodObject) {
      return func;
    }
    if (object != null) return object;
    if (descr != null) {
      if (descr instanceof TypeDescriptorGet get) return get.descrGet(this, getType());
      return descr;
    }
    return null;
  }
}
