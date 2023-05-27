package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyBoolObject;
import org.jpvm.objects.PyObject;

public interface TypeRichCompare {

  PyBoolObject richCompare(PyObject o);
}
