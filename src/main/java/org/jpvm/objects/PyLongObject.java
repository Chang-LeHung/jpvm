package org.jpvm.objects;

import org.jpvm.errors.PyException;
import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.pyinterface.TypeDoIterate;
import org.jpvm.objects.pyinterface.TypeIterable;
import org.jpvm.objects.types.PyLongType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.python.BuiltIn;

public class PyLongObject extends PyObject
        implements PyNumberMethods {

    public static PyObject type = new PyLongType();

    public static PyLongObject[] miniPool = new PyLongObject[256 + 5];

    private long data;

    public PyLongObject(long data) {
        this.data = data;
    }

    public PyLongObject(int data) {
        this.data = data;
    }

    public static PyBoolObject check(PyObject o) {
        return new PyBoolObject(o == type);
    }

    public static PyLongObject getLongObject(long num) {
        int idx = (int) num + 5;
        if (num <= 255 && num >= -5) {
            if (miniPool[idx] == null) {
                miniPool[idx] = new PyLongObject(num);
            }
            return miniPool[idx];
        } else {
            return new PyLongObject(num);
        }
    }

    public static PyLongObject getLongObject(int num) {
        return getLongObject((long) num);
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public void fromString(String s) {
        data = Integer.parseInt(s);
    }

    @Override
    public Object toJavaType() {
        return data;
    }

    @Override
    public PyObject getType() {
        return type;
    }

    @Override
    public PyUnicodeObject getTypeName() {
        return type.getTypeName();
    }

    @Override
    public PyUnicodeObject str() {
        return new PyUnicodeObject(String.valueOf(data));
    }

    @Override
    public PyUnicodeObject repr() {
        return str();
    }

    @Override
    public PyLongObject hash() {
        return new PyLongObject(this.data * 31);
    }

    @Override
    public PyBoolObject richCompare(PyObject o, Operator op) throws PyException {
        switch (op) {
            case PyCmp_IS -> {
                if (o == this)
                    return BuiltIn.True;
                return BuiltIn.False;
            }
            case PyCmp_IS_NOT -> {
                if (o != this)
                    return BuiltIn.True;
                return BuiltIn.False;
            }
        }
        if (o instanceof TypeDoIterate itr) {
            switch (op) {
                case PyCmp_IN -> {
                    while (itr.hasNext()){
                        if(itr.next().richCompare(this, Operator.Py_EQ).isTrue()) {
                            return BuiltIn.True;
                        }
                    }
                    return BuiltIn.False;
                }
                case PyCmp_NOT_IN -> {
                    while (itr.hasNext()){
                        if(itr.next().richCompare(this, Operator.Py_EQ).isTrue()) {
                            return BuiltIn.False;
                        }
                    }
                    return BuiltIn.True;
                }
            }
        }else if(o instanceof TypeIterable itr){
            TypeDoIterate iterate = itr.getIterator();
            switch (op) {
                case PyCmp_IN -> {
                    while (iterate.hasNext()){
                        if(iterate.next().richCompare(this, Operator.Py_EQ).isTrue()) {
                            return BuiltIn.True;
                        }
                    }
                    return BuiltIn.False;
                }
                case PyCmp_NOT_IN -> {
                    while (iterate.hasNext()){
                        if(iterate.next().richCompare(this, Operator.Py_EQ).isTrue()) {
                            return BuiltIn.False;
                        }
                    }
                    return BuiltIn.True;
                }
            }
        }

        if (o instanceof PyLongObject n) {
            switch (op) {
                case Py_GT -> {
                    if (data > n.getData())
                        return BuiltIn.True;
                    return BuiltIn.False;
                }
                case Py_EQ -> {
                    if (data == n.getData())
                        return BuiltIn.True;
                    return BuiltIn.False;
                }
                case Py_NE -> {
                    if (data != n.getData())
                        return BuiltIn.True;
                    return BuiltIn.False;
                }
                case Py_GE -> {
                    if (data >= n.getData())
                        return BuiltIn.True;
                    return BuiltIn.False;
                }
                case Py_LT -> {
                    if (data < n.getData())
                        return BuiltIn.True;
                    return BuiltIn.False;
                }
                case Py_LE -> {
                    if (data <= n.getData())
                        return BuiltIn.True;
                    return BuiltIn.False;
                }
            }

            return BuiltIn.False;
        }


        throw new PyUnsupportedOperator("PyLongObject not support operator " + op);
    }

    @Override
    public PyObject add(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply function add on int and " + o.getTypeName());
        return new PyLongObject(data + ((PyLongObject) o).getData());
    }

    @Override
    public PyObject sub(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply function sub on int and " + o.getTypeName());
        return new PyLongObject(data - ((PyLongObject) o).getData());
    }

    @Override
    public PyObject mul(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply function mul on int and " + o.getTypeName());
        return new PyLongObject(data * ((PyLongObject) o).getData());
    }

    @Override
    public PyObject mod(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply function mod on int and " + o.getTypeName());
        return new PyLongObject(data % ((PyLongObject) o).getData());
    }

    @Override
    public PyObject divmod(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply function divmod on int and " + o.getTypeName());
        PyTupleObject ret = new PyTupleObject(2);
        ret.set(0, trueDiv(o));
        ret.set(1, mod(o));
        return ret;
    }

    @Override
    public PyObject pow(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply function pow on int and " + o.getTypeName());
        return new PyLongObject((long) Math.pow(data, ((PyLongObject) o).getData()));
    }

    @Override
    public PyObject neg() {
        return new PyLongObject(-data);
    }

    @Override
    public PyObject pos() {
        return new PyLongObject(data);
    }

    @Override
    public PyObject abs() {
        return new PyLongObject(Math.abs(data));
    }

    @Override
    public PyObject bool() {
        return new PyBoolObject(data != 0);
    }

    @Override
    public PyObject invert() {
        return new PyLongObject(~data);
    }

    @Override
    public PyObject lshift(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply << on int and " + o.getTypeName());
        return new PyLongObject(data << ((PyLongObject) o).getData());
    }

    @Override
    public PyObject rshift(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply >> on int and " + o.getTypeName());
        return new PyLongObject(data >> ((PyLongObject) o).getData());
    }

    @Override
    public PyObject and(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply & on int and " + o.getTypeName());
        return new PyLongObject(data & ((PyLongObject) o).getData());
    }

    @Override
    public PyObject xor(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply ^ on int and " + o.getTypeName());
        return new PyLongObject(data ^ ((PyLongObject) o).getData());
    }

    @Override
    public PyObject or(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply or on int and " + o.getTypeName());
        return new PyLongObject(data | ((PyLongObject) o).getData());
    }

    @Override
    public PyObject nbInt() {
        return new PyLongObject(data);
    }

    @Override
    public PyObject nbFloat() {
        return new PyFloatObject(data);
    }

    @Override
    public PyObject floorDiv(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply / on int and " + o.getTypeName());
        return new PyLongObject(data / ((PyLongObject) o).getData());
    }

    @Override
    public PyObject trueDiv(PyObject o) throws PyTypeNotMatch {
        if (!(o instanceof PyLongObject))
            throw new PyTypeNotMatch("can apply & on int and " + o.getTypeName());
        return new PyFloatObject((double) data / ((PyLongObject) o).getData());
    }

    @Override
    public PyObject inplaceAdd(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return add(o);
    }

    @Override
    public PyObject inplaceSub(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return sub(o);
    }

    @Override
    public PyObject inplaceMul(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return mul(o);
    }

    @Override
    public PyObject inplaceMod(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return mod(o);
    }

    @Override
    public PyObject inplacePow(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return pow(o);
    }

    @Override
    public PyObject inplaceLshift(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return lshift(o);
    }

    @Override
    public PyObject inplaceRshift(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return rshift(o);
    }

    @Override
    public PyObject inplaceAnd(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return and(o);
    }

    @Override
    public PyObject inplaceXor(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return xor(o);
    }

    @Override
    public PyObject inplaceOr(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return or(o);
    }

    @Override
    public PyObject inplaceFloorDiv(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return floorDiv(o);
    }

    @Override
    public PyObject inplaceTrueDiv(PyObject o) throws PyNotImplemented, PyTypeNotMatch {
        return trueDiv(o);
    }

    @Override
    public PyObject index() {
        return new PyLongObject(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof Integer o) return data == o;
        if (obj instanceof Long o) return data == o;
        return false;
    }
}
