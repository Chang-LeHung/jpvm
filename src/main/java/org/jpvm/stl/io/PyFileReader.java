package org.jpvm.stl.io;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.lang.String;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.PyObject;
import org.jpvm.exceptions.jobjs.PyException;

import java.io.BufferedReader;
import java.io.FileReader;

public class PyFileReader extends PyObject{
    public FileReader fileReader;
    public BufferedReader bufferedReader;
    public PyFileReader(){}

    public PyFileReader(String path) throws PyException {
        try {
            fileReader=new FileReader(path);
            bufferedReader=new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            //throw new RuntimeException(e);
            PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"PyFileReader: File Not Found Error");
        }
    }
    public PyFileReader(FileDescriptor fileDescriptor) throws PyException {
        fileReader=new FileReader(fileDescriptor);
        bufferedReader=new BufferedReader(fileReader);
    }
}
