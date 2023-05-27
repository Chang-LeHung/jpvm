package org.jpvm.objects.pyinterface;

import org.jpvm.objects.PyBoolObject;

public interface PyHashable {

  default PyBoolObject isHashable() {
    return new PyBoolObject(false);
  }
}
