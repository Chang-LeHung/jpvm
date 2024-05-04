package org.jpvm.stl.io;

import org.jpvm.objects.PyObject;

import java.io.RandomAccessFile;
import java.lang.String;
public class PyOpen extends PyObject {
    private String path;
    private String mode;
    private PyFileReader pyFileReader;
    private PyFileWriter pyFileWriter;
    private RandomAccessFile randomAccessFile;
    public PyOpen(){}
    public void setRandomAccessFile(RandomAccessFile randomAccessFile) {
        this.randomAccessFile = randomAccessFile;
    }
    public RandomAccessFile getRandomAccessFile() {
        return randomAccessFile;
    }
    public void setPyFileReader(PyFileReader pyFileReader) {
        this.pyFileReader = pyFileReader;
    }
    public PyFileReader getPyFileReader() {
        return pyFileReader;
    }
    public void setPyFileWriter(PyFileWriter pyFileWriter) {
        this.pyFileWriter = pyFileWriter;
    }
    public PyFileWriter getPyFileWriter() {
        return pyFileWriter;
    }
    public void pathset(String s){
        path=s;
    }
    public String pathget(){
        return path;
    }
    public void modeset(String s){
        mode=s;
    }
    public String modeget(){
        return mode;
    }
    public void print(){
        System.out.println("path="+path);
        System.out.println("mode="+mode);
    }
    public void test(){
        System.out.println("pyopen<<<");
        System.out.println("pyopen>>>");
    }
}
