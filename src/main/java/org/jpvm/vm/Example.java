package org.jpvm.vm;

import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.vm.JPVM;
import org.jpvm.stl.io.*;
import org.jpvm.stl.math;
import java.io.IOException;

public class Example {

    public static void main(String[] args) {
        String filename = "D:\\APP\\bishe\\jpvm\\__pycache__\\pyiotest.cpython-38.pyc";
        try {
            JPVM JPVM=new JPVM(filename);
            JPVM.run();
            System.out.println("java运行完毕");
        } catch (PyException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}