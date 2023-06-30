package org.jpvm.objects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.jpvm.errors.PyException;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.python.BuiltIn;

public class Utils {

  public static PyTupleObject packSelfAsTuple(PyObject self, PyTupleObject args) {
    PyTupleObject res = new PyTupleObject(args.size() + 1);
    res.set(0, self);

    for (int i = 0; i < args.size(); i++) {
      res.set(i + 1, args.get(i));
    }
    return res;
  }

  public static PyModuleObject loadClass(String path, PyUnicodeObject name) {
    PyModuleObject res = null;
    try {
      Class<?> clazz = Class.forName(path);
      if (PyModuleObject.class.isAssignableFrom(clazz)) {
        Constructor<?> constructor = clazz.getDeclaredConstructor(PyUnicodeObject.class);
        res = (PyModuleObject) constructor.newInstance(name);
      }
    } catch (Exception ignore) {
    }
    return res;
  }

  public static PyObject loadFiled(PyObject object, PyUnicodeObject name) {
    Class<? extends PyObject> clazz = object.getClass();
    try {
      Field field = clazz.getField(name.getData());
      if (field.isAnnotationPresent(PyClassAttribute.class)) {
        field.setAccessible(true);
        Object o = field.get(object);
        return (PyObject) o;
      }
    } catch (NoSuchFieldException | IllegalAccessException ignore) {
    }
    return null;
  }

  public static PyObject loadClassMethod(PyObject object, PyUnicodeObject name) {
    Class<? extends PyObject> clazz = object.getClass();
    try {
      Method method = clazz.getMethod(name.getData(), PyObject.parameterTypes);
      if (method.isAnnotationPresent(PyClassMethod.class)) {
        return new PyMethodObject(object, method, name.getData());
      }
    } catch (NoSuchMethodException ignore) {
    }
    return null;
  }

  public static PyObject transformToPyObject(Object o) throws PyException {
    if (o instanceof Integer i) {
      return PyLongObject.getLongObject(i);
    } else if (o instanceof String s) {
      return new PyUnicodeObject(s);
    } else if (o instanceof Long l) {
      return PyLongObject.getLongObject(l);
    } else if (o instanceof Float f) {
      return new PyFloatObject(f);
    } else if (o instanceof Double d) {
      return new PyFloatObject(d);
    } else if (o instanceof Boolean b) {
      if (b) {
        return BuiltIn.True;
      }
      return BuiltIn.False;
    } else if (o instanceof List<?> list) {
      PyListObject res = new PyListObject();
      for (Object obj : list) {
        res.add(transformToPyObject(obj));
      }
      return res;
    } else if (o instanceof Object[] array) {
      PyTupleObject res = new PyTupleObject(array.length);
      for (int i = 0; i < array.length; i++) {
        res.set(i, transformToPyObject(array[i]));
      }
      return res;
    } else if (o instanceof Set set) {
      PySetObject res = new PySetObject();
      for (Object obj : set) {
        res.add(transformToPyObject(obj));
      }
      return res;
    } else if (o instanceof Map map) {
      PyDictObject res = new PyDictObject();
      for (Object obj : map.keySet()) {
        res.put(transformToPyObject(obj), transformToPyObject(map.get(obj)));
      }
      return res;
    }
    throw new PyException("can't transform" + o + " to PyObject");
  }

  public static Object transformFromPyObject(PyObject o) throws PyException {
    if (o instanceof PyLongObject l) {
      return l.getData();
    } else if (o instanceof PyUnicodeObject u) {
      return u.getData();
    } else if (o instanceof PyFloatObject f) {
      return f.getData();
    } else if (o instanceof PyBoolObject b) {
      return b.isTrue();
    } else if (o instanceof PyListObject l) {
      List<Object> res = new ArrayList<>();
      for (int i = 0; i < l.size(); i++) {
        res.add(transformFromPyObject(l.get(i)));
      }
      return res;
    } else if (o instanceof PyTupleObject t) {
      Object[] res = new Object[t.size()];
      for (int i = 0; i < t.size(); i++) {
        res[i] = transformFromPyObject(t.get(i));
      }
      return res;
    } else if (o instanceof PySetObject s) {
      Set<Object> res = new HashSet<>();
      TypeDoIterate iterator = s.getIterator();
      while (iterator.hasNext()) {
        res.add(transformFromPyObject(iterator.next()));
      }
      return res;
    } else if (o instanceof PyDictObject d) {
      Map<Object, Object> res = new HashMap<>();
      PyDictObject.PyDictItemsObject items = (PyDictObject.PyDictItemsObject) d.items();
      TypeDoIterate iterator = items.getIterator();
      while (iterator.hasNext()) {
        var next = (PyTupleObject) iterator.next();
        Object key = transformFromPyObject(next.get(0));
        Object value = transformFromPyObject(next.get(1));
        res.put(key, value);
      }
      return res;
    }
    throw new PyException("can't transform" + o.repr() + " to Object");
  }
}
