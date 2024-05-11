package org.jpvm.io;

import java.io.IOException;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.junit.Test;



public class WriteTest {
    @Test
    public void test03() throws PyException, IOException {
        String filename = "src/test/resources/io/__pycache__/test03.cpython-38.pyc";
        JPVM JPVM = new JPVM(filename);
        JPVM.run();
    }
}