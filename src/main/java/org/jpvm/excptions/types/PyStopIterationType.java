package org.jpvm.excptions.types;

import org.jpvm.excptions.PyErrorUtils;

public class PyStopIterationType extends PyExceptionType {

	public PyStopIterationType() {
		name = "StopIteration";
		addBase(0, PyErrorUtils.Exception);
	}
}
