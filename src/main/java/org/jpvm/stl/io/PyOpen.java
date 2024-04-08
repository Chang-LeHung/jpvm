package org.jpvm.stl.io;

import org.jpvm.objects.PyObject;
import org.jpvm.objects.annotation.PyClassAttribute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.lang.String;
public class PyOpen extends PyObject {
    @PyClassAttribute
    public String path;
    public String mode;
    public PyFileReader pyFileReader;
    public PyFileWriter pyFileWriter;
    public RandomAccessFile randomAccessFile;
    public PyOpen(){}
    public void print(){
        System.out.println("path="+path);
        System.out.println("mode="+mode);
    }
    public void test(){
        System.out.println("pyopen<<<");
        System.out.println("pyopen>>>");
    }
}
