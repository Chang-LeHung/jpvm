package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyTypeErrorObject extends PyExceptionObject {
	public PyTypeErrorObject(PyUnicodeObject errorMsg) {
		super(errorMsg);
	}

	public PyTypeErrorObject(String errorMsg) {
		super(errorMsg);
	}
}
