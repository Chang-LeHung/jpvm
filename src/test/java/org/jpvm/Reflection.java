package org.jpvm;


import org.jpvm.objects.PyUnicodeObject;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class Reflection {

  @Test
  public void testUnicode() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    PyUnicodeObject unicodeObject = new PyUnicodeObject("hello".getBytes(StandardCharsets.UTF_8));
    Class<? extends PyUnicodeObject> clazz = unicodeObject.getClass();
    Method getData = clazz.getMethod("getData");
    System.out.println(getData);
    Object o = getData.invoke(unicodeObject);
    System.out.println(o);
    assert o.equals("hello");
  }
}
