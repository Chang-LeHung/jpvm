package org.jpvm.stl.io;


import java.io.*;
import java.lang.Boolean;
import java.lang.reflect.Field;
import java.util.Objects;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyBytesObject;
import org.jpvm.objects.PyListObject;
import org.jpvm.exceptions.PyErrorUtils;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;
import org.jpvm.objects.types.PyTupleType;
import org.jpvm.objects.types.PyTypeType;
import org.jpvm.protocols.PyNumberMethods;


public class PyModuleMain extends PyModuleObject{
    public PyModuleMain(PyUnicodeObject moduleName) {
        super(moduleName);
    }

    @PyClassAttribute public PyObject test;

    @PyClassMethod//new
    public PyObject open(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        //注意不能传入元组，参数填写为([地址],[读取模式])，此时args为tuple类型
        //地址和读取模式类型为[str]
        //System.out.println(args.getType());
        //System.out.println(args.size());
        System.out.println("open()<<<<<<<<<<<<<<");
        //使用PyOpen存储数据
        PyOpen pyOpen=new PyOpen();
        PyTupleObject temp= (PyTupleObject) args;
        System.out.println("open读取文件为"+temp.get(0));
        System.out.println("open读取方式为"+temp.get(1));
        pyOpen.path=temp.get(0).toString();
        pyOpen.mode=temp.get(1).toString();
        switch (pyOpen.mode) {
            case "r"://只读，文件指针在开头
                pyOpen.pyFileReader=new PyFileReader(pyOpen.path);
                break;
            case "w"://只用于写入，若文件已存在则覆盖，不存在则创建
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.path);
                break;
            case "a"://用于追加，文件指针在末尾，若文件存在不覆盖，不存在则创建
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.path,true);
                break;
            case "x"://排他性创建
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.path);
                break;
            case "rb"://以二进制格式打开文件用于只读，文件指针在开头
                pyOpen.pyFileReader=new PyFileReader(pyOpen.path);
                int data;
                char temp2;
                try {

                    FileInputStream fis = new FileInputStream(pyOpen.path);
                    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                    BufferedReader bufferedReader=new BufferedReader(isr);
                    while ((data = bufferedReader.read()) != -1) {
                        temp2=(char) data;
                        System.out.println("字符为："+temp2+"data为："+data);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "wb"://以二进制格式打开文件用于写入，若文件已存在则覆盖，不存在则创建
                break;
            case "ab"://以二进制格式打开文件用于追加，文件指针在末尾，若文件存在不覆盖，不存在则创建
                break;
            case "r+"://用于读写，文件指针在开头
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    randomAccessFile.seek(0);
                    pyOpen.pyFileReader=new PyFileReader(randomAccessFile.getFD());
                    pyOpen.pyFileWriter=new PyFileWriter(randomAccessFile.getFD());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "w+"://用于读写，若文件已存在则覆盖，不存在则创建
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.path);
                pyOpen.pyFileReader=new PyFileReader(pyOpen.path);
                break;
            case "a+"://用于读写，文件指针在末尾,若文件存在不覆盖，不存在则创建
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.path,true);
                pyOpen.pyFileReader=new PyFileReader(pyOpen.path);
                break;
            case "rb+":
                break;
            case "wb+"://
                break;
            case "ab+":
                break;
            default:
                System.out.println("open读写模式错误");
                break;
        }
        System.out.println("open录入以下数据");
        pyOpen.print();
        //使用Pyio存储PyOpen
        PyTupleObject pyio = new PyTupleObject(1);
        pyio.set(0,pyOpen);
        System.out.println("open()>>>>>>>>>>>>>>");
        return pyio;
    }
    @PyClassMethod//未实现
    public PyObject fileno(PyTupleObject args, PyDictObject kwArgs) throws PyException {

        return args;
    }
    @PyClassMethod//new
    public PyObject close(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("close()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        System.out.println("path="+pyOpen.path);
        pyOpen.path="";
        if(pyOpen.pyFileWriter!=null) {
            try {
                pyOpen.pyFileWriter.bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("close()>>>>>");
        return pyio;
    }
    @PyClassMethod//new
    public PyObject closed(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("closed()<<<<<");
        PyTupleObject pyio=(PyTupleObject)args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isClosed = PyBoolObject.getInstance();
        if(Objects.equals(pyOpen.path, "")) {
            isClosed=PyBoolObject.getTrue();
        }else{
            isClosed=PyBoolObject.getFalse();
        }
        System.out.println("isClosed为"+isClosed.isTrue());
        if(isClosed.isTrue()){
            System.out.println("文件关闭成功");
        }else{
            System.out.println("文件关闭失败");
        }
        System.out.println("closed()>>>>>");
        return isClosed;
    }
    @PyClassMethod//new
    public PyObject readable(PyTupleObject args, PyDictObject kwArgs) throws PyException{
        System.out.println("readable()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isReadable = PyBoolObject.getInstance();
        if(Objects.equals(pyOpen.path, "")){
            isReadable=PyBoolObject.getFalse();
        }else{isReadable=PyBoolObject.getTrue();}
        if (isReadable.isTrue()){
            try {
                if(pyOpen.pyFileReader.fileReader.ready()){
                    isReadable=PyBoolObject.getTrue();
                }else {isReadable=PyBoolObject.getFalse();}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("isReadable="+isReadable.isTrue());
        System.out.println("readable()>>>>>");
        return isReadable;
    }
    @PyClassMethod//new
    public PyObject readline(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("readline()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyFileReader pyFileReader = pyOpen.pyFileReader;
        if (args.size()!=1){
            //此时传入的第二个参数为读取的字符数
            PyLongObject size= (PyLongObject) args.get(1);
            try {
                char[] buffer=new char[(int) size.getData()];
                pyFileReader.bufferedReader.read(buffer,0, (int) size.getData());
                System.out.println(buffer);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }else {
            try {
                String line = pyFileReader.bufferedReader.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        System.out.println("readline()>>>>>");
        return pyio;
    }
    @PyClassMethod//new
    public PyObject readlines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("readline()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyFileReader pyFileReader = pyOpen.pyFileReader;
        //readLines返回一个行列表
        PyListObject pyListObject=new PyListObject();

        if (args.size()!=1){
            //此时传入的第二个参数为读取的行数
            PyLongObject temp= (PyLongObject) args.get(1);
            int size= (int) temp.getData();
            try {
                String s;
                for(int i=0;i<size;i++){
                    if((s=pyFileReader.bufferedReader.readLine())!=null) {
                        PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(s);
                        pyListObject.add(pyUnicodeObject);
                    }else{break;}
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        }else {//读取所有行
            try {
                String s;
                do {
                    if((s=pyFileReader.bufferedReader.readLine())!=null) {
                        PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(s);
                        pyListObject.add(pyUnicodeObject);
                    }else{break;}
                }
                while(true);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        System.out.println("pyListObject="+pyListObject);
        System.out.println("readline()>>>>>");
        return pyListObject;
    }
    @PyClassMethod//废弃
    public PyObject read(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("read()<<<<<<<<<<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        System.out.println("read()>>>>>>>>>>>>>>");
        return pyio;
    }


    @PyClassMethod
    public PyObject write(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("write()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyUnicodeObject pyUnicodeObject=(PyUnicodeObject) args.get(1);
        System.out.println("write读取文件为"+pyOpen.path);
        System.out.println("write读取方式为"+pyOpen.mode);
        System.out.println("要写入的内容为: "+pyUnicodeObject);
        //写文件
        String line=pyUnicodeObject.toString();
        System.out.println(line);
        try {
            pyOpen.pyFileWriter.bufferedWriter.write(line);
            pyOpen.pyFileWriter.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"write: I/O Error");
        }

        System.out.println("write()>>>>>");
        return pyio;
    }
    @PyClassMethod
    public PyObject writelines(PyTupleObject args, PyDictObject kwArgs) throws PyException{
        System.out.println("writelines()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyListObject pyListObject=(PyListObject) args.get(1);
        System.out.println("pyListObject="+pyListObject.toString());
        try {
            for(int i=0;i<pyListObject.size();i++) {
                pyOpen.pyFileWriter.bufferedWriter.write(pyListObject.get(i).toString());
                pyOpen.pyFileWriter.bufferedWriter.newLine();
                pyOpen.pyFileWriter.bufferedWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("writelines()>>>>>");
        return pyio;
    }

}
