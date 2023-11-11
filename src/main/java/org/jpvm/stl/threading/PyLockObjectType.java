package org.jpvm.stl.threading;

import org.jpvm.excptions.objs.PyException;
import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyObject;
import org.jpvm.objects.PyTupleObject;
import org.jpvm.objects.types.PyTypeType;

public class PyLockObjectType extends PyTypeType {
	public PyLockObjectType(Class<?> clazz) {
		super(clazz);
		name = "lock";
	}

	@Override
	public PyObject call(PyObject self, PyTupleObject args, PyDictObject kwArgs) throws PyException {
		return new PyLockObject();
	}
}
