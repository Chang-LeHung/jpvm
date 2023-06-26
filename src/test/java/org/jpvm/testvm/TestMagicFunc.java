package org.jpvm.testvm;

import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;
import org.junit.Test;

import java.io.IOException;

public class TestMagicFunc {

    @Test
    public void testMagicFunc() {
        String filename = "src/test/resources/testpy/__pycache__/testMagicFunc.cpython-38.pyc";
        try {
            new PVM(filename).run();
        } catch (PyException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
