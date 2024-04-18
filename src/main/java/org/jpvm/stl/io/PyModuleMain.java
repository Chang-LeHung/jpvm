package org.jpvm.stl.io;


import java.io.*;
import java.util.Objects;

import org.jpvm.objects.PyDictObject;
import org.jpvm.objects.PyListObject;
import org.jpvm.exceptions.jobjs.PyException;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassAttribute;
import org.jpvm.objects.annotation.PyClassMethod;


public class PyModuleMain extends PyModuleObject{

    public PyModuleMain(PyUnicodeObject moduleName) {
        super(moduleName);
    }

    @PyClassAttribute public PyObject test;


    @PyClassMethod
    public PyObject open(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        // Note that tuples cannot be passed in
        // the parameters should be filled in as ([address], [read mode]), where args is of tuple type
        // The address and read mode are of type [str]
        // Use PyOpen to store data

        PyOpen pyOpen=new PyOpen();
        PyTupleObject temp= (PyTupleObject) args;
        pyOpen.pathset(temp.get(0).toString());
        pyOpen.modeset(temp.get(1).toString());
        switch (pyOpen.modeget()) {
            case "r":
                //Read-only, file pointer at the beginning, no file created
                File file = new File(pyOpen.pathget());
                if (! file.exists()) {
                    throw new RuntimeException("文件不存在，无法创建新文件");
                }
                try {
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD()));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "w":
                //Write-only, overwrite if file exists, create if file does not exist
                try{
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().setLength(0);//注意清空原文件内容
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD()));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "a":
                //For appending, file pointer at the end
                // do not overwrite if file exists, create if file does not exist
                try{
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().seek(pyOpen.getRandomAccessFile().length());
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),true));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "x":
                //Exclusive creation, cannot open if file exists, create if file does not exist
                if (new File(pyOpen.pathget()).exists()) {
                    throw new RuntimeException("文件已存在，无法创建新文件");
                }
                try{
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD()));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "rb":
                //Open file in binary format, read-only, file pointer at the beginning
                try {
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),"b"));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "wb":
                //Open file in binary format, write-only
                // overwrite if file exists, create if file does not exist
                try{
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().setLength(0);//注意清空原文件内容
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),"b"));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ab":
                //Open file in binary format,  append
                //file pointer at the end, do not overwrite if file exists, create if file does not exist
                try{
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().seek(pyOpen.getRandomAccessFile().length());
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),"b",true));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "r+":
                //For reading and writing, file pointer at the beginning
                try {
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "w+":
                //For reading and writing, overwrite if file exists, create if file does not exist
                try {
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().setLength(0);//注意清空原文件内容
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD()));
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "a+":
                //For reading and writing, file pointer at the end
                // do not overwrite if file exists, create if file does not exist
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.pathget(), "rw");
                    randomAccessFile.seek(randomAccessFile.length());
                    pyOpen.setRandomAccessFile(randomAccessFile);
                    pyOpen.setPyFileWriter(new PyFileWriter(randomAccessFile.getFD(),true));
                    pyOpen.setPyFileReader(new PyFileReader(randomAccessFile.getFD()));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "rb+":
                //Open in binary format, for reading and writing, file pointer at the beginning
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.pathget(), "rw");
                    randomAccessFile.seek(0);
                    pyOpen.setRandomAccessFile(randomAccessFile);
                    pyOpen.setPyFileReader(new PyFileReader(randomAccessFile.getFD(),"b"));
                    pyOpen.setPyFileWriter(new PyFileWriter(randomAccessFile.getFD(),"b"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "wb+":
                //Open in binary format, for reading and writing
                // overwrite if file exists, create if file does not exist
                try {
                    pyOpen.setRandomAccessFile(new RandomAccessFile(pyOpen.pathget(), "rw"));
                    pyOpen.getRandomAccessFile().setLength(0);//clear the original file content
                    pyOpen.getRandomAccessFile().seek(0);
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),"b"));
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),"b"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ab+":
                //Open in binary format, for reading and writing
                // file pointer at the end, do not overwrite if file exists, create if file does not exist
                 try {
                     RandomAccessFile randomAccessFile = new RandomAccessFile(pyOpen.pathget(), "rw");
                     randomAccessFile.seek(randomAccessFile.length());
                     pyOpen.setRandomAccessFile(randomAccessFile);
                     pyOpen.setPyFileWriter(new PyFileWriter(randomAccessFile.getFD(),"b",true));
                     pyOpen.setPyFileReader(new PyFileReader(randomAccessFile.getFD(),"b"));
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                break;
            default:
                //System.out.println("open() Read-Write Mode Error");
                break;
        }
        //Use Pyio to store PyOpen.
        PyTupleObject pyio = new PyTupleObject(1);
        pyio.set(0,pyOpen);
        return pyio;
    }


    @PyClassMethod
    public PyObject fileno(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        String s=null;
        try {
            s = pyOpen.getRandomAccessFile().getFD().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(s);
        return pyUnicodeObject;
    }
    @PyClassMethod
    public PyObject close(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        pyOpen.pathset("");
        pyOpen.setPyFileReader(null);
        pyOpen.setPyFileWriter(null);
        pyOpen.setRandomAccessFile(null);
        System.gc();
        if(pyOpen.getPyFileWriter()!=null) {
            try {
                pyOpen.getPyFileWriter().bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pyio;
    }

    @PyClassMethod
    public PyObject closed(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio=(PyTupleObject)args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isClosed;
        if(Objects.equals(pyOpen.pathget(), "")) {
            isClosed=PyBoolObject.getTrue();
        }else{
            isClosed=PyBoolObject.getFalse();
        }
        //if(isClosed.isTrue()){
        //    System.out.println("File closed successfully");
        //}else{
        //    System.out.println("File closure failed");
        //}
        return isClosed;
    }

    @PyClassMethod
    public PyObject readable(PyTupleObject args, PyDictObject kwArgs) throws PyException{
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isReadable = PyBoolObject.getInstance();
        if(Objects.equals(pyOpen.pathget(), "")){
            isReadable=PyBoolObject.getFalse();
        }else{isReadable=PyBoolObject.getTrue();}
        if (isReadable.isTrue()){
            try {
                if(pyOpen.getPyFileReader().fileReader.ready()){
                    isReadable=PyBoolObject.getTrue();
                }else {isReadable=PyBoolObject.getFalse();}
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return isReadable;
    }

    @PyClassMethod
    public PyObject readline(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyUnicodeObject pyUnicodeObject = new PyUnicodeObject("");
        if(pyOpen.modeget().contains("b")){
            //Binary
            if (args.size() != 1) {
                //At this time, the second parameter passed in is the number of characters read.
                PyLongObject size = (PyLongObject) args.get(1);
                try {
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    byte[] buffer=new byte[(int) size.getData()];
                    int i=0;
                    byte data;
                    while(i<size.getData()) {
                        data= (byte) pyOpen.getRandomAccessFile().read();
                        if(data==-1){break;}
                        buffer[i]=data;
                        i++;
                    }
                    pyUnicodeObject=new PyUnicodeObject(buffer);
                    pyOpen.getRandomAccessFile().seek(offset+i);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {
                //Read a line
                try {
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    String string = "";
                    int data;
                    do{
                        data=pyOpen.getPyFileReader().bufferedReader.read();
                        if(data==-1){break;}
                        else if (data==10) {
                            string=string+(char)data;
                            break;
                        }
                        string=string+(char)data;
                    }while(data!=-1&&data!=10);
                    //In the Windows system, the newline character is usually represented by \r\n，
                    //and its corresponding ASCII codes are 13 and 10.
                    pyUnicodeObject=new PyUnicodeObject(string);
                    pyOpen.getRandomAccessFile().seek(offset+string.length());
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        else {//Non-binary
            if (args.size() != 1) {
                //At this time, the second parameter passed in is the number of characters read.
                PyLongObject size = (PyLongObject) args.get(1);
                try {
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    byte[] buffer =new byte[(int) size.getData()];
                    pyOpen.getRandomAccessFile().read(buffer,0,(int) size.getData());
                    pyOpen.getRandomAccessFile().seek(offset+size.getData());
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyUnicodeObject=new PyUnicodeObject(buffer);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {
                //Read a line
                try {
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    String line = pyOpen.getPyFileReader().bufferedReader.readLine();
                    pyOpen.getRandomAccessFile().seek(offset+line.length());
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyUnicodeObject=new PyUnicodeObject(line);
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        return pyUnicodeObject;
    }

    @PyClassMethod
    public PyObject readlines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        //readLines returns a list of lines
        PyListObject pyListObject=new PyListObject();
        if(pyOpen.modeget().contains("b")) {
            //Binary
            if (args.size() != 1) {
                //The second parameter passed in at this time is the number of lines to be read.
                PyLongObject temp = (PyLongObject) args.get(1);
                int size = (int) temp.getData();
                try {
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    StringBuilder string = new StringBuilder();
                    int data,i=0;
                    do{
                        data=pyOpen.getPyFileReader().bufferedReader.read();
                        if(data==-1){
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            break;
                        }
                        else if (data==10) {
                            //In the Windows system, the newline character is usually represented by \r\n，
                            //and its corresponding ASCII codes are 13 and 10.
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
                    pyOpen.getRandomAccessFile().seek(offset);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {
                //Read all lines
                try {
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    StringBuilder string = new StringBuilder();
                    int data;
                    do{
                        data=pyOpen.getPyFileReader().bufferedReader.read();
                        if(data==-1){
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            break;
                        }
                        else if (data==10) {
                            //In the Windows system, the newline character is usually represented by \r\n，
                            //and its corresponding ASCII codes are 13 and 10.
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
                    pyOpen.getRandomAccessFile().seek(offset);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }else{
            //Non-binary
            if (args.size() != 1) {
                //The second parameter passed in at this time is the number of lines to be read.
                PyLongObject temp = (PyLongObject) args.get(1);
                int size = (int) temp.getData();
                try {
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    String s;
                    for (int i = 0; i < size; i++) {
                        if ((s = pyOpen.getPyFileReader().bufferedReader.readLine()) != null) {
                            offset=offset+s.length();
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(s);
                            pyListObject.add(pyUnicodeObject);
                        } else {
                            break;
                        }
                    }
                    pyOpen.getRandomAccessFile().seek(offset);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            } else {
                //Read all lines
                try {
                    //BufferedReader.read will read to the end of the file
                    //and requires manually setting the file pointer.
                    long offset=pyOpen.getRandomAccessFile().getFilePointer();
                    StringBuilder string = new StringBuilder();
                    int data;
                    do{
                        data=pyOpen.getPyFileReader().bufferedReader.read();
                        if(data==-1){
                            PyUnicodeObject pyUnicodeObject = new PyUnicodeObject(string.toString());
                            pyListObject.add(pyUnicodeObject);
                            break;
                        }
                        else if (data==10) {
                            //In the Windows system, the newline character is usually represented by \r\n，
                            //and its corresponding ASCII codes are 13 and 10.
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
                    pyOpen.getRandomAccessFile().seek(offset);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        return pyListObject;
    }

    @PyClassMethod
    public PyObject seek(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio = (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyLongObject offset = (PyLongObject) args.get(1);
        PyLongObject whence = null;
        if (args.size() == 3) {
            whence = (PyLongObject) args.get(2);
            if(whence.getData()==0){}
            //Calculate from the beginning
            else if (whence.getData()==1) {
                //Calculate from the current position
                try {
                    long position = pyOpen.getRandomAccessFile().getFilePointer();
                    position= offset.getData()+position;
                    pyOpen.getRandomAccessFile().seek(position);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (whence.getData()==2) {
                //Calculate from the end
                try {
                    long position = pyOpen.getRandomAccessFile().length();
                    position= offset.getData()+position;
                    pyOpen.getRandomAccessFile().seek(position);
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(args.size() == 3){
            if(whence.getData()==0){
                //Calculate from the beginning
                try {
                    pyOpen.getRandomAccessFile().seek(offset.getData());
                    pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                    pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            //Calculate from the beginning
            try {
                pyOpen.getRandomAccessFile().seek(offset.getData());
                long position = pyOpen.getRandomAccessFile().getFilePointer();
                pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pyio;
    }

    @PyClassMethod
    //If the stream supports random access return True.
    // When seekable is false, seek (), tell () and truncate () are not supported.
    public PyObject seekable(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyBoolObject isseekable;
        switch(pyOpen.modeget()){
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
    @PyClassMethod
    public PyObject tell(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        try {
            long position = pyOpen.getRandomAccessFile().getFilePointer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pyio;
    }
    @PyClassMethod
    public PyObject truncate(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio= (PyTupleObject) args.get(0);
        PyOpen pyOpen= (PyOpen) pyio.get(0);
        PyLongObject newSize= (PyLongObject) args.get(1);
        try {
            pyOpen.getRandomAccessFile().setLength(newSize.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pyio;
    }

    @PyClassMethod
    public PyObject write(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio = (PyTupleObject) args.get(0);
        PyOpen pyOpen = (PyOpen) pyio.get(0);
        String s = (String) args.get(1).toString();
        PyLongObject len= PyLongObject.getLongObject(s.length());
        if (pyOpen.modeget().contains("b")) {
            //Binary
            try {
                long offset=pyOpen.getRandomAccessFile().getFilePointer();
                offset=offset+s.length();
                pyOpen.getRandomAccessFile().writeBytes(s);
                pyOpen.getRandomAccessFile().seek(offset);
                pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //Non-binary
            try {
                long offset=pyOpen.getRandomAccessFile().getFilePointer();
                offset=offset+s.length();
                pyOpen.getRandomAccessFile().writeBytes(s);
                pyOpen.getRandomAccessFile().seek(offset);
                pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return len;
    }

    @PyClassMethod
    public PyObject writelines(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        PyTupleObject pyio = (PyTupleObject) args.get(0);
        PyOpen pyOpen = (PyOpen) pyio.get(0);
        PyListObject pyListObject = (PyListObject) args.get(1);
        if (pyOpen.modeget().contains("b")) {
            //Binary
            try {
                long offset=pyOpen.getRandomAccessFile().getFilePointer();
                for (int i = 0; i < pyListObject.size(); i++) {
                    offset=offset+pyListObject.get(i).toString().length();
                    String s=pyListObject.get(i).toString();
                    pyOpen.getRandomAccessFile().writeBytes(s);
                }
                pyOpen.getRandomAccessFile().seek(offset);
                pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //Non-binary
            try {
                long offset=pyOpen.getRandomAccessFile().getFilePointer();
                for (int i = 0; i < pyListObject.size(); i++) {
                    offset=offset+pyListObject.get(i).toString().length();
                    String s=pyListObject.get(i).toString();
                    pyOpen.getRandomAccessFile().writeBytes(s);
                }
                pyOpen.getRandomAccessFile().seek(offset);
                pyOpen.setPyFileReader(new PyFileReader(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
                pyOpen.setPyFileWriter(new PyFileWriter(pyOpen.getRandomAccessFile().getFD(),pyOpen.modeget()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pyio;
    }
}
