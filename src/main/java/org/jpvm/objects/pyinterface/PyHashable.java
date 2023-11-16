package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyBoolObject;
import org.jpvm.python.BuiltIn;

public interface PyHashable {

  default PyBoolObject isHashable() {
    return BuiltIn.False;
  }
}
