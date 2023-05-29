package org.jpvm.objects;


import org.jpvm.errors.PyUnsupportedOperator;
import org.jpvm.objects.pyinterface.*;
import org.jpvm.objects.types.PyBaseObjectType;
import org.jpvm.python.BuiltIn;

/**
 * base class of all classes in python
 */
public class PyObject extends Exception implements PyArgs, TypeCheck,
      TypeName, TypeStr, TypeRepr, TypeHash,TypeRichCompare,
      TypeNew, TypeInit, TypeCall, PyHashable {

   public static Object type = new PyBaseObjectType();

   /**
    * base class name of all classes in python
    */
   public static PyUnicodeObject name;

   private PyDictObject dict;

   private PyTupleObject bases;

   private PyListObject mro;

   private PyLongObject hashcode;

   @Override
   public String toString() {
      return "<PyObject>";
   }

   @Override
   public Object toJavaType() {
      return null;
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
      if (name == null) {
         name = new PyUnicodeObject("object");
      }
      return name;
   }

   @Override
   public PyUnicodeObject str() {
      return getTypeName();
   }

   @Override
   public PyUnicodeObject repr() {
      return getTypeName();
   }

   @Override
   public PyLongObject hash() {
      return new PyLongObject(0);
   }

   @Override
   public PyBoolObject richCompare(PyObject o, Operator op) throws PyUnsupportedOperator {
      if (op == Operator.PY_EQ) {
         if (o == this)
            return BuiltIn.True;
         return BuiltIn.False;
      }
      throw new PyUnsupportedOperator("not support operator " + op);
   }

   public PyDictObject getDict() {
      return dict;
   }

   public PyTupleObject getBases() {
      return bases;
   }

   public PyListObject getMro() {
      return mro;
   }

   public void setDict(PyDictObject dict) {
      this.dict = dict;
   }

   public void setBases(PyTupleObject bases) {
      this.bases = bases;
   }

   public void setMro(PyListObject mro) {
      this.mro = mro;
   }

   @Override
   public int hashCode() {
      return (int) hash().getData();
   }
}
