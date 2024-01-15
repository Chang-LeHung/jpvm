package org.jpvm.exceptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyValueErrorObject extends PyExceptionObject {
	public PyValueErrorObject(PyUnicodeObject errorMsg) {
		super(errorMsg);
	}

	public PyValueErrorObject(String errorMsg) {
		super(errorMsg);
	}
}
