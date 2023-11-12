package org.jpvm.excptions.pyobjs;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyUnicodeObject;

public class PyExceptionObject extends PyObject {

	private final PyUnicodeObject errorMsg;

	public PyExceptionObject(PyUnicodeObject errorMsg) {
		this.errorMsg = errorMsg;
	}

	public PyExceptionObject(String errorMsg) {
		this.errorMsg = new PyUnicodeObject(errorMsg);
	}

	@Override
	public PyUnicodeObject repr() {
		return errorMsg;
	}

	@Override
	public PyUnicodeObject str() {
		return errorMsg;
	}
}
