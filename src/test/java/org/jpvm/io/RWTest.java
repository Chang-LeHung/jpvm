package org.jpvm.io;

import java.io.IOException;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;



public class RWTest {
    @Test
    public void test04() throws PyException, IOException {
        String filename = "src/test/resources/io/__pycache__/test04.cpython-38.pyc";
        JPVM JPVM = new JPVM(filename);
        JPVM.run();
    }
}
