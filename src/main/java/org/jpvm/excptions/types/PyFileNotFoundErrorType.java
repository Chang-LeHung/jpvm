package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyFileNotFoundErrorType extends PyExceptionType {

	public PyFileNotFoundErrorType() {
		name = "FileNotFoundError";
		addBase(0, PyErrorUtils.Exception);
	}
}
