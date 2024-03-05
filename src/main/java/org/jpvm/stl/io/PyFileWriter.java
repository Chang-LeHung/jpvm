package org.jpvm.stl.io;

import java.io.*;
import java.lang.String;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.types.PyFileNotFoundErrorType;
import org.jpvm.objects.PyObject;
import org.jpvm.exceptions.jobjs.PyException;
public class PyFileWriter extends PyObject{
    public FileWriter fileWriter;
    public BufferedWriter bufferedWriter;
    public PyFileWriter(){}
    public PyFileWriter(String path) throws PyException {
        try {
            fileWriter=new FileWriter(path);
            bufferedWriter=new BufferedWriter(fileWriter);
            System.out.println("fileWriter="+fileWriter);
            System.out.println("bufferedWriter="+bufferedWriter);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"PyFileWriter: IOException Error");
        }
    }
    public PyFileWriter(String path,boolean bool) throws PyException {
        try {
            fileWriter=new FileWriter(path,bool);
            bufferedWriter=new BufferedWriter(fileWriter);
            System.out.println("fileWriter="+fileWriter);
            System.out.println("bufferedWriter="+bufferedWriter);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"PyFileWriter: IOException Error");
        }
    }
    public PyFileWriter(FileDescriptor fileDescriptor){
        fileWriter=new FileWriter(fileDescriptor);
        bufferedWriter=new BufferedWriter(fileWriter);
    }
}
