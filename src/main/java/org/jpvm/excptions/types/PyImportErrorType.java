package org.jpvm.excptions.types;

import org.jpvm.python.BuiltIn;

public class PyImportErrorType extends PyPythonBaseExceptionType {

	public PyImportErrorType() {
		name = "ImportError";
		addBase(0, BuiltIn.loadFromDict("Exception"));
	}
}
