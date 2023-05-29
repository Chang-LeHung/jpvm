package org.jpvm.objects;

import org.jpvm.errors.PyNotImplemented;
import org.jpvm.errors.PyTypeNotMatch;
import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.types.PyFloatType;
import org.jpvm.protocols.PyNumberMethods;
import org.jpvm.python.BuiltIn;

public class PyFloatObject extends PyObject implements PyNumberMethods {

   public static PyObject type = new PyFloatType();

   private double data;
   public PyFloatObject(float data) {
      this.data = data;
   }

   public PyFloatObject(double data) {
      this.data = data;
   }

   public double getData() {
      return data;
   }


   public void setData(double data) {
      this.data = data;
   }

   public void setData(float data) {
      this.data = data;
   }

   @Override
   public String toString() {
      return String.valueOf(data);
   }

   @Override
   public Object toJavaType() {
      return data;
   }

   @Override
   public Object getType() {
      return type;
   }

   public static PyBoolObject check(PyObject o) {
      return new PyBoolObject(o == type);
   }

   @Override
   public PyUnicodeObject getTypeName() {
      return type.getTypeName();
   }

   @Override
   public PyUnicodeObject str() {
      return new PyUnicodeObject(Double.toString(data));
   }

   @Override
   public PyUnicodeObject repr() {
      return str();
   }

   @Override
   public PyLongObject hash() {
      return new PyLongObject((long)data * 31);
   }

   @Override
   public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
      if (op == Operator.PY_EQ) {
         if (!(o instanceof PyFloatObject))
            return BuiltIn.False;
         if (((PyFloatObject) o).getData() == data)
            return BuiltIn.True;
         return BuiltIn.False;
      }
      throw new PyUnsupportedOperator("not support operator " + op);
   }

   @Override
   public PyObject add(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw new PyTypeNotMatch("can not apply + on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("add not implemented");
      }
      return new PyFloatObject(data+((PyFloatObject)object).getData());
   }

   @Override
   public PyObject sub(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw  new PyTypeNotMatch("can not apply - on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("parameter o not implement function nbFloat");
      }
      return new PyFloatObject(data-((PyFloatObject)object).getData());
   }

   @Override
   public PyObject mul(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw  new PyTypeNotMatch("can not apply * on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("parameter o not implement function nbFloat");
      }
      return new PyFloatObject(data*((PyFloatObject)object).getData());
   }

   @Override
   public PyObject mod(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw  new PyTypeNotMatch("can not apply % on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("parameter o not implement function nbFloat");
      }
      return new PyFloatObject(data-((PyFloatObject)object).getData());
   }

   @Override
   public PyObject divmod(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      PyTupleObject tuple = new PyTupleObject(2);
      tuple.set(0, trueDiv(o));
      tuple.set(1, mod(o));
      return tuple;
   }

   @Override
   public PyObject pow(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw  new PyTypeNotMatch("can not apply ** on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("parameter o not implement function nbFloat");
      }
      return new PyFloatObject(Math.pow(data, ((PyFloatObject)object).getData()));
   }

   @Override
   public PyObject neg() {
      return new PyFloatObject(-data);
   }

   @Override
   public PyObject pos() {
      return new PyFloatObject(data);
   }

   @Override
   public PyObject abs() {
      return new PyFloatObject(Math.abs(data));
   }

   @Override
   public PyObject bool() {
      return new PyBoolObject(data != 0);
   }

   @Override
   public PyObject nbInt() {
      return new PyLongObject((long)data);
   }

   @Override
   public PyObject nbFloat() {
      return new PyFloatObject(data);
   }

   @Override
   public PyObject floorDiv(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw  new PyTypeNotMatch("can not apply % on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("parameter o not implement function nbFloat");
      }
      return new PyLongObject((long)(data / ((PyFloatObject)object).getData()));
   }

   @Override
   public PyObject trueDiv(PyObject o) throws PyTypeNotMatch, PyNotImplemented {
      if (!(o instanceof PyNumberMethods))
         throw  new PyTypeNotMatch("can not apply % on float and " + o.getTypeName());
      PyObject object;
      try {
         object = ((PyNumberMethods) o).nbFloat();
      } catch (PyNotImplemented e) {
         throw new PyNotImplemented("parameter o not implement function nbFloat");
      }
      return new PyFloatObject(data / ((PyFloatObject)object).getData());
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) return false;
      if (obj == this) return true;
      if (obj instanceof Integer o) return data == o;
      if (obj instanceof Long o) return data == o;
      if (obj instanceof Double o) return data == o;
      if (obj instanceof Float o) return data == o;
      return false;
   }
}
