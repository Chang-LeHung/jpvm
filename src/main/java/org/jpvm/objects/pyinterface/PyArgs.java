package org.jpvm.objects.pyinterface;

public interface PyArgs {

  /**
   * this method is used for transforming {@link org.jpvm.objects.PyObject}
   * to native Java type
   *
   * @return {@link Object}
   */
  Object toJavaType();
}
