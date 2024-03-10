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
    FileOutputStream fileOutputStream;
    OutputStreamWriter outputStreamWriter;
    public PyFileWriter(){}
    public PyFileWriter(String path) throws PyException {
        try {
            fileWriter=new FileWriter(path);
            bufferedWriter=new BufferedWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"PyFileWriter: IOException Error");
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
    public PyFileWriter(FileDescriptor fileDescriptor,boolean bool) throws PyException {
        fileWriter=new FileWriter(fileDescriptor);
        bufferedWriter=new BufferedWriter(fileWriter);
    }
    public PyFileWriter(FileDescriptor fileDescriptor,String mod,boolean bool) throws PyException {
        try {
            fileOutputStream = new FileOutputStream(fileDescriptor);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public PyFileWriter(FileDescriptor fileDescriptor){
        fileWriter=new FileWriter(fileDescriptor);
        bufferedWriter=new BufferedWriter(fileWriter);
    }
    public PyFileWriter(FileDescriptor fileDescriptor,String mod){
        try {
            fileOutputStream = new FileOutputStream(fileDescriptor);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public PyFileWriter(String path,String mod) {
        try {
            fileOutputStream = new FileOutputStream(path);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public PyFileWriter(String path,String mod,boolean bool) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
            randomAccessFile.seek(randomAccessFile.length()); // 将文件指针放在文件最后
            fileOutputStream = new FileOutputStream(randomAccessFile.getFD());
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
