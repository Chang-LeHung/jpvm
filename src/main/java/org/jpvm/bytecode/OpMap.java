package org.jpvm.bytecode;

import java.util.HashMap;
import java.util.Map;

public class OpMap {

   public enum OpName {
      POP_TOP, ROT_TWO, ROT_THREE, DUP_TOP, DUP_TOP_TWO,
      ROT_FOUR, NOP, UNARY_POSITIVE, UNARY_NEGATIVE, UNARY_NOT,
      UNARY_INVERT, BINARY_MATRIX_MULTIPLY, INPLACE_MATRIX_MULTIPLY,
      BINARY_POWER, BINARY_MULTIPLY, BINARY_MODULO, BINARY_ADD,
      BINARY_SUBTRACT, BINARY_SUBSCR, BINARY_FLOOR_DIVIDE, BINARY_TRUE_DIVIDE,
      INPLACE_FLOOR_DIVIDE, INPLACE_TRUE_DIVIDE, GET_LEN, MATCH_MAPPING, MATCH_SEQUENCE,
      MATCH_KEYS, COPY_DICT_WITHOUT_KEYS, WITH_EXCEPT_START, GET_AITER,
      GET_ANEXT, BEFORE_ASYNC_WITH, END_ASYNC_FOR, INPLACE_ADD,
      INPLACE_SUBTRACT, INPLACE_MULTIPLY, INPLACE_MODULO, STORE_SUBSCR,
      DELETE_SUBSCR, BINARY_LSHIFT, BINARY_RSHIFT, BINARY_AND,
      BINARY_XOR, BINARY_OR, INPLACE_POWER, GET_ITER, GET_YIELD_FROM_ITER,
      PRINT_EXPR, LOAD_BUILD_CLASS, YIELD_FROM, GET_AWAITABLE,
      LOAD_ASSERTION_ERROR, INPLACE_LSHIFT, INPLACE_RSHIFT, INPLACE_AND,
      INPLACE_XOR, INPLACE_OR, LIST_TO_TUPLE, RETURN_VALUE, IMPORT_STAR,
      SETUP_ANNOTATIONS, YIELD_VALUE, POP_BLOCK, POP_EXCEPT, STORE_NAME,
      DELETE_NAME, UNPACK_SEQUENCE, FOR_ITER, UNPACK_EX, STORE_ATTR,
      DELETE_ATTR, STORE_GLOBAL, DELETE_GLOBAL, ROT_N, LOAD_CONST,
      LOAD_NAME, BUILD_TUPLE, BUILD_LIST, BUILD_SET, BUILD_MAP,
      LOAD_ATTR, COMPARE_OP, IMPORT_NAME, IMPORT_FROM, JUMP_FORWARD,
      JUMP_IF_FALSE_OR_POP, JUMP_IF_TRUE_OR_POP, JUMP_ABSOLUTE, POP_JUMP_IF_FALSE,
      POP_JUMP_IF_TRUE, LOAD_GLOBAL, IS_OP, CONTAINS_OP, RERAISE,
      JUMP_IF_NOT_EXC_MATCH, SETUP_FINALLY, LOAD_FAST, STORE_FAST,
      DELETE_FAST, GEN_START, RAISE_VARARGS, CALL_FUNCTION, MAKE_FUNCTION,
      BUILD_SLICE, LOAD_CLOSURE, LOAD_DEREF, STORE_DEREF, DELETE_DEREF,
      CALL_FUNCTION_KW, CALL_FUNCTION_EX, SETUP_WITH, EXTENDED_ARG,
      LIST_APPEND, SET_ADD, MAP_ADD, LOAD_CLASSDEREF, MATCH_CLASS,
      SETUP_ASYNC_WITH, FORMAT_VALUE, BUILD_CONST_KEY_MAP, BUILD_STRING,
      LOAD_METHOD, CALL_METHOD, LIST_EXTEND, SET_UPDATE, DICT_MERGE,
      DICT_UPDATE
   }
   public static int HAVE_ARGUMENT = 90;
   public static int EXTENDED_ARG = 144;
   public static Map<Integer, OpName> instructions = new HashMap<>();

   static {
      instructions.put(1, OpName.POP_TOP);
      instructions.put(2, OpName.ROT_TWO);
      instructions.put(3, OpName.ROT_THREE);
      instructions.put(4, OpName.DUP_TOP);
      instructions.put(5, OpName.DUP_TOP_TWO);
      instructions.put(6, OpName.ROT_FOUR);
      instructions.put(9, OpName.NOP);
      instructions.put(10, OpName.UNARY_POSITIVE);
      instructions.put(11, OpName.UNARY_NEGATIVE);
      instructions.put(12, OpName.UNARY_NOT);
      instructions.put(15, OpName.UNARY_INVERT);
      instructions.put(16, OpName.BINARY_MATRIX_MULTIPLY);
      instructions.put(17, OpName.INPLACE_MATRIX_MULTIPLY);
      instructions.put(19, OpName.BINARY_POWER);
      instructions.put(20, OpName.BINARY_MULTIPLY);
      instructions.put(22, OpName.BINARY_MODULO);
      instructions.put(23, OpName.BINARY_ADD);
      instructions.put(24, OpName.BINARY_SUBTRACT);
      instructions.put(25, OpName.BINARY_SUBSCR);
      instructions.put(26, OpName.BINARY_FLOOR_DIVIDE);
      instructions.put(27, OpName.BINARY_TRUE_DIVIDE);
      instructions.put(28, OpName.INPLACE_FLOOR_DIVIDE);
      instructions.put(29, OpName.INPLACE_TRUE_DIVIDE);
      instructions.put(30, OpName.GET_LEN);
      instructions.put(31, OpName.MATCH_MAPPING);
      instructions.put(32, OpName.MATCH_SEQUENCE);
      instructions.put(33, OpName.MATCH_KEYS);
      instructions.put(34, OpName.COPY_DICT_WITHOUT_KEYS);
      instructions.put(49, OpName.WITH_EXCEPT_START);
      instructions.put(50, OpName.GET_AITER);
      instructions.put(51, OpName.GET_ANEXT);
      instructions.put(52, OpName.BEFORE_ASYNC_WITH);
      instructions.put(54, OpName.END_ASYNC_FOR);
      instructions.put(55, OpName.INPLACE_ADD);
      instructions.put(56, OpName.INPLACE_SUBTRACT);
      instructions.put(57, OpName.INPLACE_MULTIPLY);
      instructions.put(59, OpName.INPLACE_MODULO);
      instructions.put(60, OpName.STORE_SUBSCR);
      instructions.put(61, OpName.DELETE_SUBSCR);
      instructions.put(62, OpName.BINARY_LSHIFT);
      instructions.put(63, OpName.BINARY_RSHIFT);
      instructions.put(64, OpName.BINARY_AND);
      instructions.put(65, OpName.BINARY_XOR);
      instructions.put(66, OpName.BINARY_OR);
      instructions.put(67, OpName.INPLACE_POWER);
      instructions.put(68, OpName.GET_ITER);
      instructions.put(69, OpName.GET_YIELD_FROM_ITER);
      instructions.put(70, OpName.PRINT_EXPR);
      instructions.put(71, OpName.LOAD_BUILD_CLASS);
      instructions.put(72, OpName.YIELD_FROM);
      instructions.put(73, OpName.GET_AWAITABLE);
      instructions.put(74, OpName.LOAD_ASSERTION_ERROR);
      instructions.put(75, OpName.INPLACE_LSHIFT);
      instructions.put(76, OpName.INPLACE_RSHIFT);
      instructions.put(77, OpName.INPLACE_AND);
      instructions.put(78, OpName.INPLACE_XOR);
      instructions.put(79, OpName.INPLACE_OR);
      instructions.put(82, OpName.LIST_TO_TUPLE);
      instructions.put(83, OpName.RETURN_VALUE);
      instructions.put(84, OpName.IMPORT_STAR);
      instructions.put(85, OpName.SETUP_ANNOTATIONS);
      instructions.put(86, OpName.YIELD_VALUE);
      instructions.put(87, OpName.POP_BLOCK);
      instructions.put(89, OpName.POP_EXCEPT);
      instructions.put(90, OpName.STORE_NAME);
      instructions.put(91, OpName.DELETE_NAME);
      instructions.put(92, OpName.UNPACK_SEQUENCE);
      instructions.put(93, OpName.FOR_ITER);
      instructions.put(94, OpName.UNPACK_EX);
      instructions.put(95, OpName.STORE_ATTR);
      instructions.put(96, OpName.DELETE_ATTR);
      instructions.put(97, OpName.STORE_GLOBAL);
      instructions.put(98, OpName.DELETE_GLOBAL);
      instructions.put(99, OpName.ROT_N);
      instructions.put(100, OpName.LOAD_CONST);
      instructions.put(101, OpName.LOAD_NAME);
      instructions.put(102, OpName.BUILD_TUPLE);
      instructions.put(103, OpName.BUILD_LIST);
      instructions.put(104, OpName.BUILD_SET);
      instructions.put(105, OpName.BUILD_MAP);
      instructions.put(106, OpName.LOAD_ATTR);
      instructions.put(107, OpName.COMPARE_OP);
      instructions.put(108, OpName.IMPORT_NAME);
      instructions.put(109, OpName.IMPORT_FROM);
      instructions.put(110, OpName.JUMP_FORWARD);
      instructions.put(111, OpName.JUMP_IF_FALSE_OR_POP);
      instructions.put(112, OpName.JUMP_IF_TRUE_OR_POP);
      instructions.put(113, OpName.JUMP_ABSOLUTE);
      instructions.put(114, OpName.POP_JUMP_IF_FALSE);
      instructions.put(115, OpName.POP_JUMP_IF_TRUE);
      instructions.put(116, OpName.LOAD_GLOBAL);
      instructions.put(117, OpName.IS_OP);
      instructions.put(118, OpName.CONTAINS_OP);
      instructions.put(119, OpName.RERAISE);
      instructions.put(121, OpName.JUMP_IF_NOT_EXC_MATCH);
      instructions.put(122, OpName.SETUP_FINALLY);
      instructions.put(124, OpName.LOAD_FAST);
      instructions.put(125, OpName.STORE_FAST);
      instructions.put(126, OpName.DELETE_FAST);
      instructions.put(129, OpName.GEN_START);
      instructions.put(130, OpName.RAISE_VARARGS);
      instructions.put(131, OpName.CALL_FUNCTION);
      instructions.put(132, OpName.MAKE_FUNCTION);
      instructions.put(133, OpName.BUILD_SLICE);
      instructions.put(135, OpName.LOAD_CLOSURE);
      instructions.put(136, OpName.LOAD_DEREF);
      instructions.put(137, OpName.STORE_DEREF);
      instructions.put(138, OpName.DELETE_DEREF);
      instructions.put(141, OpName.CALL_FUNCTION_KW);
      instructions.put(142, OpName.CALL_FUNCTION_EX);
      instructions.put(143, OpName.SETUP_WITH);
      instructions.put(144, OpName.EXTENDED_ARG);
      instructions.put(145, OpName.LIST_APPEND);
      instructions.put(146, OpName.SET_ADD);
      instructions.put(147, OpName.MAP_ADD);
      instructions.put(148, OpName.LOAD_CLASSDEREF);
      instructions.put(152, OpName.MATCH_CLASS);
      instructions.put(154, OpName.SETUP_ASYNC_WITH);
      instructions.put(155, OpName.FORMAT_VALUE);
      instructions.put(156, OpName.BUILD_CONST_KEY_MAP);
      instructions.put(157, OpName.BUILD_STRING);
      instructions.put(160, OpName.LOAD_METHOD);
      instructions.put(161, OpName.CALL_METHOD);
      instructions.put(162, OpName.LIST_EXTEND);
      instructions.put(163, OpName.SET_UPDATE);
      instructions.put(164, OpName.DICT_MERGE);
      instructions.put(165, OpName.DICT_UPDATE);
   }
}
