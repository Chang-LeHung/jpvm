package org.jpvm.stl.random;

import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.python.BuiltIn;

public class PyModuleMain extends PyModuleObject {
	public PyModuleMain(PyUnicodeObject moduleName) {
		super(moduleName);
	}

	@PyClassMethod
	public PyObject test(PyTupleObject args, PyDictObject kwArgs) {
    System.out.println("Hello World");
		return BuiltIn.None;
	}
}
