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
    //@PyClassAttribute public String path;

    //根据这个测试，可以使用字典作为返回值，测试字典位于args元组的[0]位置
    @PyClassMethod
    public PyObject test(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("test()<<<<<<<<<<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen test=(PyOpen) pyio.get(2);
        test.test();
        System.out.println("test.path="+test.path);

        System.out.println("test()>>>>>>>>>>>>>>");
        return pyio;
    }
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
            case "r":
                pyOpen.pyFileReader= new PyFileReader(pyOpen.path);
                break;
            case "w":
                break;
            case "a":
                break;
            case "x":
                break;
            case "rb":
                break;
            case "wb":
                break;
            case "ab":
                break;
            case "r+":
                break;
            case "w+":
                break;
            case "a+":
                break;
            case "rb+":
                break;
            case "wb+":
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
        System.out.println("close()>>>>>");
        return pyio;
    }
    @PyClassMethod//new
    public PyObject closed(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("closed()<<<<<");
        PyTupleObject pyio=(PyTupleObject)args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isClosed = PyBoolObject.getInstance();
        isClosed.setBool(Objects.equals(pyOpen.path, ""));
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
            isReadable.setBool(false);
        }else{isReadable.setBool(true);}
        if (isReadable.isTrue()){
            try {
                if(pyOpen.pyFileReader.fileReader.ready()){
                    isReadable.setBool(true);
                }else {isReadable.setBool(false);}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("isReadable="+isReadable.isTrue());
        System.out.println("readable()>>>>>");
        return isReadable;
    }
    @PyClassMethod//new
    public PyObject readLine(PyTupleObject args, PyDictObject kwArgs) throws PyException {
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
    public PyObject readLines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
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
                for(int i=0;i<size;i++){
                    PyString pyString = new PyString();
                    pyString.string = pyFileReader.bufferedReader.readLine();
                    if(pyString.string==null){break;}
                    pyListObject.add(pyString);
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        }else {//读取所有行
            try {
                do {
                    PyString pyString = new PyString();
                    pyString.string = pyFileReader.bufferedReader.readLine();
                    if(pyString.string==null){break;}
                    pyListObject.add(pyString);
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
    @PyClassMethod
    public PyObject read(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("read()<<<<<<<<<<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        System.out.println("read读取文件为"+pyio.get(0));
        System.out.println("read读取方式为"+pyio.get(1));
        //读取文件内容
        FileReader fr=null;
        BufferedReader br=null;
        String path=pyio.get(0).toString();
        String path2 = "D:\\APP\\bishe\\jpvm\\"+pyio.get(0);
        //
        System.out.println("test<<<<<<<<<<<<<<");
        try {
            PyFileReader test = new PyFileReader(path);
            String line = test.bufferedReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = test.bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        System.out.println("test>>>>>>>>>>>>>>>");
        //
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
        System.out.println("read()>>>>>>>>>>>>>>");
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
