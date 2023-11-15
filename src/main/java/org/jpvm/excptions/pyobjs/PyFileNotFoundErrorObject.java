package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyFileNotFoundErrorObject extends PyExceptionObject {
	public PyFileNotFoundErrorObject(PyUnicodeObject errorMsg) {
		super(errorMsg);
	}

	public PyFileNotFoundErrorObject(String errorMsg) {
		super(errorMsg);
	}
}
