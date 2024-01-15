package org.jpvm.stl.io;


import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.lang.Boolean;

import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.types.PyTupleType;
import org.jpvm.objects.types.PyTypeType;

public class PyModuleMain extends PyModuleObject{
    public PyModuleMain(PyUnicodeObject moduleName) {
        super(moduleName);
    }

    @PyClassAttribute public PyObject test;
    @PyClassMethod
    public PyObject open(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        //注意不能传入元组，参数填写为([地址],[读取模式])，此时args为tuple类型
        //地址和读取模式类型为[str]
        //System.out.println(args.getType());
        //System.out.println(args.size());
        System.out.println("open()<<<<<<<<<<<<<<");
        PyTupleObject pyio = new PyTupleObject(0);
        pyio=(PyTupleObject) pyio.add(args);
        System.out.println("open读取文件为"+pyio.get(0));
        System.out.println("open读取方式为"+pyio.get(1));
        switch(pyio.get(1).toString()){
            case "r": break;
            case "w": break;
            case "a": break;
            case "rb": break;
            case "wb": break;
            case "ab": break;
            case "r+": break;
            case "w+": break;
            case "a+": break;
            case "rb+": break;
            case "wb+": break;
            case "ab+": break;
            default:
                System.out.println("open读写模式错误");
                break;
        }
        System.out.println("open录入以下数据");
        for (int i = 0; i < pyio.size(); i++) {
            System.out.println(pyio.get(i));
        }
        System.out.println("open()>>>>>>>>>>>>>");
        return pyio;
    }
    @PyClassMethod
    public PyObject close(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio=new PyTupleObject(0);
        System.out.println("文件已关闭");
        return pyio;
    }
    @PyClassMethod
    public PyObject closed(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio=new PyTupleObject(0);
        Boolean isClosed;
        if(pyio.size()==0){
            isClosed =true;
        }else {
            isClosed =false;
        }
        if(isClosed){
            System.out.println("文件关闭成功");
        }else{
            System.out.println("文件关闭失败");
        }
        return pyio;
    }
    @PyClassMethod
    public PyObject readLine(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        //System.out.println("readLine读取文件为"+pyio.get(0));
        System.out.println("readLine读取方式为"+pyio.get(1));
        //读取文件内容
        FileReader fr=null;
        BufferedReader br=null;
        String path = "D:\\APP\\bishe\\jpvm\\"+pyio.get(0);
        try{
            fr=new FileReader(path);
            br=new BufferedReader(fr);
            String line=br.readLine();
            System.out.println("readline读取内容为\n"+line);
        }catch(IOException ioe){
            ioe.getStackTrace();
        }
        try{
            br.close();
            fr.close();
        }catch (IOException ioe){
            ioe.getStackTrace();
        }
        return pyio;
    }
    @PyClassMethod
    public PyObject read(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        System.out.println("read读取文件为"+pyio.get(0));
        System.out.println("read读取方式为"+pyio.get(1));
        //读取文件内容
        FileReader fr=null;
        BufferedReader br=null;
        String path = "D:\\APP\\bishe\\jpvm\\"+pyio.get(0);
        try{
            fr=new FileReader(path);
            br=new BufferedReader(fr);
            String line=br.readLine();
            while(line!=null){
                System.out.println(line);
                line=br.readLine();
            }
        }catch(IOException ioe){
            ioe.getStackTrace();
        }
        try{
            br.close();
            fr.close();
        }catch (IOException ioe){
            ioe.getStackTrace();
        }
        return pyio;
    }

    @PyClassMethod
    public PyObject readLines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        System.out.println("readLines读取文件为"+pyio.get(0));
        System.out.println("readLines读取方式为"+pyio.get(1));
        long hunt=((PyLongObject) args.get(1)).getData();
        System.out.println("需要打印行数为"+hunt);
        //读取文件内容
        FileReader fr=null;
        BufferedReader br=null;
        String path = "D:\\APP\\bishe\\jpvm\\"+pyio.get(0);
        long num=0;
        try{
            fr=new FileReader(path);
            br=new BufferedReader(fr);
            String line=br.readLine();
            while(line!=null&&num<hunt){//这里使用hint表示需要输出多少行
                System.out.println(line);
                line=br.readLine();
                num++;
                System.out.println("当前是第"+num+"行");
            }
        }catch(IOException ioe){
            ioe.getStackTrace();
        }
        try{
            br.close();
            fr.close();
        }catch (IOException ioe){
            ioe.getStackTrace();
        }
        return pyio;
    }
    @PyClassMethod
    public PyObject write(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        System.out.println("write读取文件为"+pyio.get(0));
        System.out.println("write读取方式为"+pyio.get(1));
        String beWrite=args.get(1).toString();
        System.out.println("要写入的内容为"+beWrite);
        //写文件
        FileWriter fw=null;
        BufferedWriter bw=null;
        try{//当FileWriter第二个参数为TRUE时使用附加方式写入，否则覆盖原内容
            fw=new FileWriter("D:\\APP\\bishe\\jpvm\\"+pyio.get(0),true);
            bw=new BufferedWriter(fw);
            bw.write(beWrite);

        }catch (IOException ioe){
            ioe.getStackTrace();
        }
        try{
            bw.flush();
            bw.close();
            fw.close();
        }catch (IOException ioe){
            ioe.getStackTrace();
        }
        return pyio;
    }

}
