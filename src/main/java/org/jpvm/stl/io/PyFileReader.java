package org.jpvm.stl.io;

import java.io.*;
import java.lang.String;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.objects.PyObject;
import org.jpvm.exceptions.jobjs.PyException;

public class PyFileReader extends PyObject{
    public FileReader fileReader;
    public BufferedReader bufferedReader;
    public FileInputStream fileInputStream;
    public InputStreamReader inputStreamReader;
    public PyFileReader(){}

    public PyFileReader(String path) throws PyException {//Non binary
        try {
            fileReader=new FileReader(path);
            bufferedReader=new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            //PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"PyFileReader: File Not Found Error");
        }
    }
    public PyFileReader(FileDescriptor fileDescriptor) throws PyException {//Non binary
        fileReader=new FileReader(fileDescriptor);
        bufferedReader=new BufferedReader(fileReader);
    }
    public PyFileReader(FileDescriptor fileDescriptor,String mod) throws PyException {//binary
        try {
            fileInputStream = new FileInputStream(fileDescriptor);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public PyFileReader(String path,String mod) throws PyException{//binary
        try {
            fileInputStream = new FileInputStream(path);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
