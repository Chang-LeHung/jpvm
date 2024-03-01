package org.jpvm.stl.io;


import java.io.*;
import java.lang.Boolean;
import java.lang.reflect.Field;
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


    //根据这个测试，可以使用字典作为返回值，测试字典位于args元组的[0]位置
    @PyClassMethod
    public PyObject test(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("test()<<<<<<<<<<<<<<");
        PyTupleObject test=args;
        System.out.println(test.get(0));
        PyDictObject pyDictObject=(PyDictObject) test.get(0);
        PyObject fileno=new PyBytesObject("1".getBytes());
        PyObject mod=new PyBytesObject("2".getBytes());
        System.out.println(pyDictObject.get(fileno));
        System.out.println(pyDictObject.get(mod));
        System.out.println("test()>>>>>>>>>>>>>>");
        return test;
    }
    @PyClassMethod
    public PyObject open(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        //注意不能传入元组，参数填写为([地址],[读取模式])，此时args为tuple类型
        //地址和读取模式类型为[str]
        //System.out.println(args.getType());
        //System.out.println(args.size());
        System.out.println("open()<<<<<<<<<<<<<<");
        PyTupleObject pyio = new PyTupleObject(0);
        pyio=(PyTupleObject) pyio.add(args);
        System.out.println(pyio.toString());
        System.out.println("open读取文件为"+pyio.get(0));
        System.out.println("open读取方式为"+pyio.get(1));
        String path = pyio.get(0).toString();
        switch (pyio.get(1).toString()) {
            case "r":
                PyFileReader fileReader = new PyFileReader(path);
                PyTupleObject temp = new PyTupleObject(1);
                temp.set(0,fileReader);
                //此时fileReader变为一个PyObject对象，使用时需要转换为PyFileReader
                pyio=(PyTupleObject) pyio.add(temp);
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
        for (int i = 0; i < pyio.size(); i++) {
            System.out.println(pyio.get(i));
        }
        System.out.println("open()>>>>>>>>>>>>>>");
        return pyio;
    }
    @PyClassMethod//未实现
    public PyObject fileno(PyTupleObject args, PyDictObject kwArgs) throws PyException {

        return args;
    }
    @PyClassMethod
    public PyObject close(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("close()<<<<<");
        PyTupleObject pyio=new PyTupleObject(0);
        args.set(0,pyio);
        System.out.println("args为"+args);
        System.out.println(pyio.size());
        System.out.println("文件已关闭");
        System.out.println("close()>>>>>");
        return pyio;
    }
    @PyClassMethod
    public PyObject closed(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("closed()<<<<<");
        PyTupleObject pyio=(PyTupleObject)args.get(0);
        System.out.println("args"+args);
        System.out.println("pyio为"+pyio.size());
        PyBoolObject isClosed = PyBoolObject.getInstance();
        System.out.println(pyio.size());
        if(pyio.size()==0){
            isClosed.setBool(true);
        }else {
            isClosed.setBool(false);
        }
        System.out.println("isclosed为"+isClosed.isTrue());
        if(isClosed.isTrue()){
            System.out.println("文件关闭成功");
        }else{
            System.out.println("文件关闭失败");
        }
        System.out.println("closed()>>>>>");
        return isClosed;
    }
    @PyClassMethod
    public PyObject readLine(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("readline()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyFileReader pyFileReader = (PyFileReader) pyio.get(2);
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
    @PyClassMethod
    public PyObject readLines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        System.out.println("readline()<<<<<");
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyFileReader pyFileReader = (PyFileReader) pyio.get(2);
        if (args.size()!=1){
            //此时传入的第二个参数为读取的行数
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
