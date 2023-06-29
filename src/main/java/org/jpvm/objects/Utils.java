package org.jpvm.objects;

import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Utils {

  public static PyTupleObject packSelfAsTuple(PyObject self, PyTupleObject args) {
    PyTupleObject res = new PyTupleObject(args.size() + 1);
    res.set(0, self);

    for (int i = 0; i < args.size(); i++) {
      res.set(i + 1, args.get(i));
    }
    return res;
  }

  public static PyModuleObject loadClass(String path) {
    PyModuleObject res = null;
    try {
      Class<?> clazz = Class.forName(path);
      if (PyModuleObject.class.isAssignableFrom(clazz)) {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        res = (PyModuleObject) constructor.newInstance();
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
}
