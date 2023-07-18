package org.jpvm.module.filestream;

import java.io.*;
import org.jpvm.errors.PyException;
import org.jpvm.excptions.PyErrorUtils;
import org.jpvm.objects.*;
import org.jpvm.objects.annotation.PyClassMethod;

public class PyFileOpenObject extends PyObject {
    private int index;

    private final String path;

    /**
     * 'r' 'rw' 'a' 'w'
     */
    private final String mode;
    private int tal = 0;
    private RandomAccessFile rf;

    public PyFileOpenObject(String path) {
        this.index = 0;
        this.path = path;
        this.mode = "r";
    }

    public PyFileOpenObject(String path, String mod) {
        this.index = 0;
        this.path = path;
        this.mode = mod;

    }


    @PyClassMethod
    public PyObject read(PyTupleObject args, PyDictObject kwArgs) throws PyException, IOException {
        if (mode.equals("w") || mode.equals("a")) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "can not read file in write mode ");
        }
        if (rf == null) {
            try {
                rf = new RandomAccessFile(path, "r");
                if (index == 0) {
                    String res = "";
                    String line = "";
                    while ((line = rf.readLine()) != null) {
                        res = res + line + "\n";
                    }
                    index += res.length();
                    return new PyUnicodeObject(res);
                } else {
                    PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, "must read from head");
                    return null;
                }
            } catch (FileNotFoundException e) {
                PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getMessage());
                return null;
            } catch (IOException e) {
                PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
                return null;
            }
        } else {
            if (index == 0) {
                rf.close();
                rf = new RandomAccessFile(path, "r");
                String res = "";
                String line = "";
                while ((line = rf.readLine()) != null) {
                    res = res + line + "\n";
                }
                index += res.length();
                return new PyUnicodeObject(res);
            }
            if (index == rf.length()) {
                return new PyUnicodeObject("");
            } else {
                String res = "";
                String line = "";
                while ((line = rf.readLine()) != null) {
                    res = res + line + "\n";
                }
                index += res.length();
                return new PyUnicodeObject(res);
            }
        }
    }



    @PyClassMethod
    public PyObject readline(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        if (args.size() != 0) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "no need argument");
        }
        if (mode.equals("w")) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "can not read file in write mode ");
        }
        try {
            if (rf == null) {
                rf = new RandomAccessFile(path, "r");
            }
            String s = rf.readLine();
            if (s == null) {
                return new PyUnicodeObject("");
            }
            index = s.length() + "\n".length();
            return new PyUnicodeObject(s);
        } catch (FileNotFoundException e) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.Exception, e.getMessage());
            return null;
        } catch (IOException e) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
            return null;
        }
    }


    @PyClassMethod
    public PyObject write(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        if (args.size() != 1) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "only need one argument");
            return null;
        }
        if (mode.equals("r")) {
            PyErrorUtils.pyErrorFormat(PyErrorUtils.TypeError, "can not write file in write mode ");
        }
        if (tal == 0 && !mode.equals("a")) {
            try (BufferedWriter wf = new BufferedWriter(new FileWriter(path))) {
                String s = args.get(0).toString();
                tal = 1;
                wf.write(s);
                return new PyUnicodeObject("1");
            } catch (IOException e) {
                PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
                return null;
            }
        } else {
            try (BufferedWriter wf = new BufferedWriter(new FileWriter(path, true))) {
                String s = args.get(0).toString();
                wf.write(s);
                return new PyUnicodeObject("1");
            } catch (IOException e) {
                PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
                return null;
            }
        }
    }

    @PyClassMethod
    public PyObject close(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        if (rf != null) {
            try {
                rf.close();
            } catch (IOException e) {
                PyErrorUtils.pyErrorFormat(PyErrorUtils.RuntimeError, e.getMessage());
            }
        }
        return new PyUnicodeObject("1");
    }

    @PyClassMethod
    public PyObject seek(PyTupleObject args, PyDictObject kwArgs) throws PyException {
        if (args.size() == 1) {
            PyLongObject o = (PyLongObject) args.get(0);
            if (o.getData() != 0) {
                PyErrorUtils.pyErrorFormat(PyErrorUtils.ValueError, "only support 0");
            }
            index = (int) o.getData();
            return new PyUnicodeObject("1");
        }
        PyErrorUtils.pyErrorFormat(PyErrorUtils.AttributeError, "only support one argument");
        return null;
    }
}
