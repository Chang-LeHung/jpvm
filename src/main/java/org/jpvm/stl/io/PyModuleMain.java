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
        //使用PyOpen存储数据
        PyOpen pyOpen=new PyOpen();
        PyTupleObject temp= (PyTupleObject) args;
        pyOpen.path=temp.get(0).toString();
        pyOpen.mode=temp.get(1).toString();
        switch (pyOpen.mode) {
            case "r"://只读，文件指针在开头
                try {
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "w"://只用于写入，若文件已存在则覆盖，不存在则创建
                try{
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "a"://用于追加，文件指针在末尾，若文件存在不覆盖，不存在则创建
                try{
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),true);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "x"://排他性创建//待处理
                try{
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "rb"://以二进制格式打开文件用于只读，文件指针在开头
                try {
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),"b");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "wb"://以二进制格式打开文件用于写入，若文件已存在则覆盖，不存在则创建
                try{
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),"b");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ab"://以二进制格式打开文件用于追加，文件指针在末尾，若文件存在不覆盖，不存在则创建
                try{
                    pyOpen.randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),"b",true);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "r+"://用于读写，文件指针在开头
                try {
                    pyOpen.randomAccessFile=new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD());
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "w+"://用于读写，若文件已存在则覆盖，不存在则创建
                try {
                    pyOpen.randomAccessFile=new RandomAccessFile(pyOpen.path, "rw");
                    pyOpen.randomAccessFile.seek(0);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD());
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "a+"://用于读写，文件指针在末尾,若文件存在不覆盖，不存在则创建
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    randomAccessFile.seek(randomAccessFile.length());
                    pyOpen.randomAccessFile=randomAccessFile;
                    pyOpen.pyFileWriter=new PyFileWriter(randomAccessFile.getFD(),true);
                    pyOpen.pyFileReader=new PyFileReader(randomAccessFile.getFD());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "rb+"://二进制格式打开用于读写，文件指针在开头
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    randomAccessFile.seek(0);
                    pyOpen.randomAccessFile=randomAccessFile;
                    pyOpen.pyFileReader=new PyFileReader(randomAccessFile.getFD(),"b");
                    pyOpen.pyFileWriter=new PyFileWriter(randomAccessFile.getFD(),"b");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "wb+"://二进制格式打开用于读写，若文件已存在则覆盖，不存在则创建
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                    randomAccessFile.seek(0);
                    pyOpen.randomAccessFile=randomAccessFile;
                    pyOpen.pyFileWriter=new PyFileWriter(randomAccessFile.getFD(),"b");
                    pyOpen.pyFileReader=new PyFileReader(randomAccessFile.getFD(),"b");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ab+"://二进制格式打开用于读写，文件指针在末尾,若文件存在不覆盖，不存在则创建
                 try {
                     RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.path, "rw");
                     randomAccessFile.seek(randomAccessFile.length());
                     pyOpen.randomAccessFile=randomAccessFile;
                     pyOpen.pyFileWriter=new PyFileWriter(randomAccessFile.getFD(),"b",true);
                     pyOpen.pyFileReader=new PyFileReader(randomAccessFile.getFD(),"b");
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                //pyFileWriter=new PyFileWriter(pyOpen.path,"b",true);
                //pyOpen.pyFileReader=new PyFileReader(pyOpen.path,"b");
                break;
            default:
                System.out.println("open读写模式错误");
                break;
        }

        //使用Pyio存储PyOpen
        PyTupleObject pyio = new PyTupleObject(1);
        pyio.set(0,pyOpen);
        return pyio;
    }
    @PyClassMethod//未实现
    public PyObject fileno(PyTupleObject args, PyDictObject kwArgs) throws PyException {

        return args;
    }
    @PyClassMethod//new
    public PyObject close(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        pyOpen.path="";
        if(pyOpen.pyFileWriter!=null) {
            try {
                pyOpen.pyFileWriter.bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pyio;
    }
    @PyClassMethod//new
    public PyObject closed(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio=(PyTupleObject)args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isClosed;
        if(Objects.equals(pyOpen.path, "")) {
            isClosed=PyBoolObject.getTrue();
        }else{
            isClosed=PyBoolObject.getFalse();
        }
        if(isClosed.isTrue()){
            System.out.println("文件关闭成功");
        }else{
            System.out.println("文件关闭失败");
        }
        return isClosed;
    }
    @PyClassMethod//new
    public PyObject readable(PyTupleObject args, PyDictObject kwArgs) throws PyException{
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
        return isReadable;
    }
    @PyClassMethod//new
    public PyObject readline(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyUnicodeObject pyUnicodeObject = new PyUnicodeObject("");
        if(pyOpen.mode.contains("b")){//二进制
            if (args.size() != 1) {
                //此时传入的第二个参数为读取的字符数
                PyLongObject size = (PyLongObject) args.get(1);
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.read将读取到文件末尾，需要手动设置文件指针
                    char[] buffer = new char[(int) size.getData()];
                    int i=0,data;
                    while(i<size.getData()) {
                        data=pyOpen.pyFileReader.bufferedReader.read();
                        if(data==-1){break;}
                        buffer[i]=(char)data;
                        i++;
                    }
                    pyUnicodeObject=new PyUnicodeObject(buffer.toString());
                    pyOpen.randomAccessFile.seek(offset+i);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {//读取一行
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.read将读取到文件末尾，需要手动设置文件指针
                    String string = "";
                    int data;
                    do{
                        data=pyOpen.pyFileReader.bufferedReader.read();
                        if(data==-1){break;}
                        else if (data==10) {
                            string=string+(char)data;
                            break;
                        }
                        string=string+(char)data;
                    }while(data!=-1&&data!=10);//Windows系统中，换行符通常表示为\r\n，对应的ASCII码分别是13和10。
                    pyUnicodeObject=new PyUnicodeObject(string);
                    pyOpen.randomAccessFile.seek(offset+string.length());
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        else {//非二进制
            if (args.size() != 1) {
                //此时传入的第二个参数为读取的字符数
                PyLongObject size = (PyLongObject) args.get(1);
                try {
                    char[] buffer = new char[(int) size.getData()];
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.read将读取到文件末尾，需要手动设置文件指针
                    pyOpen.pyFileReader.bufferedReader.read(buffer, 0, (int) size.getData());
                    pyOpen.randomAccessFile.seek(offset+size.getData());
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyUnicodeObject=new PyUnicodeObject(buffer.toString());
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {//读取一行
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.readLine将读取到文件末尾，需要手动设置文件指针
                    String line = pyOpen.pyFileReader.bufferedReader.readLine();
                    pyOpen.randomAccessFile.seek(offset+line.length());
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyUnicodeObject=new PyUnicodeObject(line);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        return pyUnicodeObject;
    }
    @PyClassMethod//new
    public PyObject readlines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        //PyFileReader pyFileReader = pyOpen.pyFileReader;
        //readLines返回一个行列表
        PyListObject pyListObject=new PyListObject();
        if(pyOpen.mode.contains("b")) {//二进制
            if (args.size() != 1) {
                //此时传入的第二个参数为读取的行数
                PyLongObject temp = (PyLongObject) args.get(1);
                int size = (int) temp.getData();
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.readLine将读取到文件末尾，需要手动设置文件指针
                    StringBuilder string = new StringBuilder();
                    int data,i=0;
                    do{
                        data=pyOpen.pyFileReader.bufferedReader.read();
                        if(data==-1){
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            break;
                        }
                        else if (data==10) {//Windows系统中，换行符通常表示为\r\n，对应的ASCII码分别是13和10。
                            i++;
                            offset++;
                            string.append((char) data);
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            string = new StringBuilder();
                            continue;
                        }
                        string.append((char) data);
                        offset++;
                    }while(data!=-1&&i<size);
                    pyOpen.randomAccessFile.seek(offset);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {//读取所有行
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.readLine将读取到文件末尾，需要手动设置文件指针
                    StringBuilder string = new StringBuilder();
                    int data;
                    do{
                        data=pyOpen.pyFileReader.bufferedReader.read();
                        if(data==-1){
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            break;
                        }
                        else if (data==10) {//Windows系统中，换行符通常表示为\r\n，对应的ASCII码分别是13和10。
                            offset++;
                            string.append((char) data);
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            string= new StringBuilder();
                            continue;
                        }
                        offset++;
                        string.append((char) data);
                    }while(data!=-1);
                    pyOpen.randomAccessFile.seek(offset);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }else{//非二进制
            if (args.size() != 1) {
                //此时传入的第二个参数为读取的行数
                PyLongObject temp = (PyLongObject) args.get(1);
                int size = (int) temp.getData();
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.readLine将读取到文件末尾，需要手动设置文件指针
                    String s;
                    for (int i = 0; i < size; i++) {
                        if ((s = pyOpen.pyFileReader.bufferedReader.readLine()) != null) {
                            offset=offset+s.length();
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(s);
                            pyListObject.add(pyUnicodeObject);
                        } else {
                            break;
                        }
                    }
                    pyOpen.randomAccessFile.seek(offset);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {//读取所有行
                try {
                    long offset=pyOpen.randomAccessFile.getFilePointer();//bufferedReader.readLine将读取到文件末尾，需要手动设置文件指针
                    StringBuilder string = new StringBuilder();
                    int data;
                    do{
                        data=pyOpen.pyFileReader.bufferedReader.read();
                        if(data==-1){
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            break;
                        }
                        else if (data==10) {//Windows系统中，换行符通常表示为\r\n，对应的ASCII码分别是13和10。
                            offset++;
                            string.append((char) data);
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            string= new StringBuilder();
                            continue;
                        }
                        offset++;
                        string.append((char) data);
                    }while(data!=-1);
                    pyOpen.randomAccessFile.seek(offset);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        return pyListObject;
    }
    @PyClassMethod//new
    public PyObject seek(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio = (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyLongObject offset = (PyLongObject) args.get(1);
        PyLongObject whence = null;
        if (args.size() == 3) {
            whence = (PyLongObject) args.get(2);
            if(whence.getData()==0){}//从开头计算
            else if (whence.getData()==1) {//从当前计算
                try {
                    long position = pyOpen.randomAccessFile.getFilePointer();
                    position= offset.getData()+position;
                    pyOpen.randomAccessFile.seek(position);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (whence.getData()==2) {//从末尾计算
                try {
                    long position = pyOpen.randomAccessFile.length();
                    position= offset.getData()+position;
                    pyOpen.randomAccessFile.seek(position);
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(args.size() == 3){
            if(whence.getData()==0){//从开头计算
                try {
                    pyOpen.randomAccessFile.seek(offset.getData());
                    pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                    pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{//从开头计算
            try {
                pyOpen.randomAccessFile.seek(offset.getData());
                long position = pyOpen.randomAccessFile.getFilePointer();
                pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pyio;
    }
    @PyClassMethod//如果流支持随机访问则返回True，当seekable返回false时，则seek(),tell()和truncate()将引发OSError
    public PyObject seekable(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isseekable;
        switch(pyOpen.mode){
            case "r+":
            case "w+":
            case "a+":
            case "rb+":
            case "wb+":
            case "ab+":
                isseekable=PyBoolObject.getTrue();
                break;
            default:
                isseekable=PyBoolObject.getFalse();
                break;
        }
        return isseekable;
    }
    @PyClassMethod//new
    public PyObject tell(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        try {
            long position = pyOpen.randomAccessFile.getFilePointer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pyio;
    }
    @PyClassMethod//new
    public PyObject truncate(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyLongObject newSize= (PyLongObject) args.get(1);
        try {
            pyOpen.randomAccessFile.setLength(newSize.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pyio;
    }
    @PyClassMethod//没有该函数
    public PyObject write(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyUnicodeObject pyUnicodeObject=(PyUnicodeObject) args.get(1);
        //写文件
        String line=pyUnicodeObject.toString();
        try {
            pyOpen.pyFileWriter.bufferedWriter.write(line);
            pyOpen.pyFileWriter.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //PyErrorUtils.pyErrorFormat(PyErrorUtils.FileNotFoundError,"write: I/O Error");
        }

        return pyio;
    }
    @PyClassMethod
    public PyObject writelines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio = (PyTupleObject) args.get(0);
        PyOpen pyOpen = (PyOpen) pyio.get(0);
        PyListObject pyListObject = (PyListObject) args.get(1);
        if (pyOpen.mode.contains("b")) {//二进制
            try {
                long offset=pyOpen.randomAccessFile.getFilePointer();
                for (int i = 0; i < pyListObject.size(); i++) {
                    offset=offset+pyListObject.get(i).toString().length();
                    pyOpen.pyFileWriter.bufferedWriter.write(pyListObject.get(i).toString());
                    pyOpen.pyFileWriter.bufferedWriter.flush();
                }
                pyOpen.randomAccessFile.seek(offset);
                pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{//非二进制
            try {
                long offset=pyOpen.randomAccessFile.getFilePointer();
                for (int i = 0; i < pyListObject.size(); i++) {
                    offset=offset+pyListObject.get(i).toString().length();
                    pyOpen.pyFileWriter.bufferedWriter.write(pyListObject.get(i).toString());
                    pyOpen.pyFileWriter.bufferedWriter.newLine();
                    pyOpen.pyFileWriter.bufferedWriter.flush();
                }
                pyOpen.randomAccessFile.seek(offset);
                pyOpen.pyFileReader=new PyFileReader(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
                pyOpen.pyFileWriter=new PyFileWriter(pyOpen.randomAccessFile.getFD(),pyOpen.mode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pyio;
    }
}
