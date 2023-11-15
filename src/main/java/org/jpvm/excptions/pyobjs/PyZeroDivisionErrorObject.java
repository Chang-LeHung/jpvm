package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyUnicodeObject;

public class PyZeroDivisionErrorObject extends PyExceptionObject {
	public PyZeroDivisionErrorObject(PyUnicodeObject errorMsg) {
		super(errorMsg);
	}

	public PyZeroDivisionErrorObject(String errorMsg) {
		super(errorMsg);
	}
}
