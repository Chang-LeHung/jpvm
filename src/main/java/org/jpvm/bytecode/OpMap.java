package org.jpvm.bytecode;

import java.util.HashMap;
import java.util.Map;

public class OpMap {

  public static int HAVE_ARGUMENT = 90;
  public static int EXTENDED_ARG = 144;
  public static Map<Integer, OpName> instructions = new HashMap<>();

  public static int POP_TOP = 1;
  public static int ROT_TWO = 2;
  public static int ROT_THREE = 3;
  public static int DUP_TOP = 4;
  public static int DUP_TOP_TWO = 5;
  public static int ROT_FOUR = 6;
  public static int NOP = 9;
  public static int UNARY_POSITIVE = 10;
  public static int UNARY_NEGATIVE = 11;
  public static int UNARY_NOT = 12;
  public static int UNARY_INVERT = 15;
  public static int BINARY_MATRIX_MULTIPLY = 16;
  public static int INPLACE_MATRIX_MULTIPLY = 17;
  public static int BINARY_POWER = 19;
  public static int BINARY_MULTIPLY = 20;
  public static int BINARY_MODULO = 22;
  public static int BINARY_ADD = 23;
  public static int BINARY_SUBTRACT = 24;
  public static int BINARY_SUBSCR = 25;
  public static int BINARY_FLOOR_DIVIDE = 26;
  public static int BINARY_TRUE_DIVIDE = 27;
  public static int INPLACE_FLOOR_DIVIDE = 28;
  public static int INPLACE_TRUE_DIVIDE = 29;
  public static int GET_AITER = 50;
  public static int GET_ANEXT = 51;
  public static int BEFORE_ASYNC_WITH = 52;
  public static int BEGIN_FINALLY = 53;
  public static int END_ASYNC_FOR = 54;
  public static int INPLACE_ADD = 55;
  public static int INPLACE_SUBTRACT = 56;
  public static int INPLACE_MULTIPLY = 57;
  public static int INPLACE_MODULO = 59;
  public static int STORE_SUBSCR = 60;
  public static int DELETE_SUBSCR = 61;
  public static int BINARY_LSHIFT = 62;
  public static int BINARY_RSHIFT = 63;
  public static int BINARY_AND = 64;
  public static int BINARY_XOR = 65;
  public static int BINARY_OR = 66;
  public static int INPLACE_POWER = 67;
  public static int GET_ITER = 68;
  public static int GET_YIELD_FROM_ITER = 69;
  public static int PRINT_EXPR = 70;
  public static int LOAD_BUILD_CLASS = 71;
  public static int YIELD_FROM = 72;
  public static int GET_AWAITABLE = 73;
  public static int INPLACE_LSHIFT = 75;
  public static int INPLACE_RSHIFT = 76;
  public static int INPLACE_AND = 77;
  public static int INPLACE_XOR = 78;
  public static int INPLACE_OR = 79;
  public static int WITH_CLEANUP_START = 81;
  public static int WITH_CLEANUP_FINISH = 82;
  public static int RETURN_VALUE = 83;
  public static int IMPORT_STAR = 84;
  public static int SETUP_ANNOTATIONS = 85;
  public static int YIELD_VALUE = 86;
  public static int POP_BLOCK = 87;
  public static int END_FINALLY = 88;
  public static int POP_EXCEPT = 89;
  public static int STORE_NAME = 90;
  public static int DELETE_NAME = 91;
  public static int UNPACK_SEQUENCE = 92;
  public static int FOR_ITER = 93;
  public static int UNPACK_EX = 94;
  public static int STORE_ATTR = 95;
  public static int DELETE_ATTR = 96;
  public static int STORE_GLOBAL = 97;
  public static int DELETE_GLOBAL = 98;
  public static int LOAD_CONST = 100;
  public static int LOAD_NAME = 101;
  public static int BUILD_TUPLE = 102;
  public static int BUILD_LIST = 103;
  public static int BUILD_SET = 104;
  public static int BUILD_MAP = 105;
  public static int LOAD_ATTR = 106;
  public static int COMPARE_OP = 107;
  public static int IMPORT_NAME = 108;
  public static int IMPORT_FROM = 109;
  public static int JUMP_FORWARD = 110;
  public static int JUMP_IF_FALSE_OR_POP = 111;
  public static int JUMP_IF_TRUE_OR_POP = 112;
  public static int JUMP_ABSOLUTE = 113;
  public static int POP_JUMP_IF_FALSE = 114;
  public static int POP_JUMP_IF_TRUE = 115;
  public static int LOAD_GLOBAL = 116;
  public static int SETUP_FINALLY = 122;
  public static int LOAD_FAST = 124;
  public static int STORE_FAST = 125;
  public static int DELETE_FAST = 126;
  public static int RAISE_VARARGS = 130;
  public static int CALL_FUNCTION = 131;
  public static int MAKE_FUNCTION = 132;
  public static int BUILD_SLICE = 133;
  public static int LOAD_CLOSURE = 135;
  public static int LOAD_DEREF = 136;
  public static int STORE_DEREF = 137;
  public static int DELETE_DEREF = 138;
  public static int CALL_FUNCTION_KW = 141;
  public static int CALL_FUNCTION_EX = 142;
  public static int SETUP_WITH = 143;
  public static int LIST_APPEND = 145;
  public static int SET_ADD = 146;
  public static int MAP_ADD = 147;
  public static int LOAD_CLASSDEREF = 148;
  public static int BUILD_LIST_UNPACK = 149;
  public static int BUILD_MAP_UNPACK = 150;
  public static int BUILD_MAP_UNPACK_WITH_CALL = 151;
  public static int BUILD_TUPLE_UNPACK = 152;
  public static int BUILD_SET_UNPACK = 153;
  public static int SETUP_ASYNC_WITH = 154;
  public static int FORMAT_VALUE = 155;
  public static int BUILD_CONST_KEY_MAP = 156;
  public static int BUILD_STRING = 157;
  public static int BUILD_TUPLE_UNPACK_WITH_CALL = 158;
  public static int LOAD_METHOD = 160;
  public static int CALL_METHOD = 161;
  public static int CALL_FINALLY = 162;
  public static int POP_FINALLY = 163;

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
    instructions.put(50, OpName.GET_AITER);
    instructions.put(51, OpName.GET_ANEXT);
    instructions.put(52, OpName.BEFORE_ASYNC_WITH);
    instructions.put(53, OpName.BEGIN_FINALLY);
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
    instructions.put(75, OpName.INPLACE_LSHIFT);
    instructions.put(76, OpName.INPLACE_RSHIFT);
    instructions.put(77, OpName.INPLACE_AND);
    instructions.put(78, OpName.INPLACE_XOR);
    instructions.put(79, OpName.INPLACE_OR);
    instructions.put(81, OpName.WITH_CLEANUP_START);
    instructions.put(82, OpName.WITH_CLEANUP_FINISH);
    instructions.put(83, OpName.RETURN_VALUE);
    instructions.put(84, OpName.IMPORT_STAR);
    instructions.put(85, OpName.SETUP_ANNOTATIONS);
    instructions.put(86, OpName.YIELD_VALUE);
    instructions.put(87, OpName.POP_BLOCK);
    instructions.put(88, OpName.END_FINALLY);
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
    instructions.put(122, OpName.SETUP_FINALLY);
    instructions.put(124, OpName.LOAD_FAST);
    instructions.put(125, OpName.STORE_FAST);
    instructions.put(126, OpName.DELETE_FAST);
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
    instructions.put(149, OpName.BUILD_LIST_UNPACK);
    instructions.put(150, OpName.BUILD_MAP_UNPACK);
    instructions.put(151, OpName.BUILD_MAP_UNPACK_WITH_CALL);
    instructions.put(152, OpName.BUILD_TUPLE_UNPACK);
    instructions.put(153, OpName.BUILD_SET_UNPACK);
    instructions.put(154, OpName.SETUP_ASYNC_WITH);
    instructions.put(155, OpName.FORMAT_VALUE);
    instructions.put(156, OpName.BUILD_CONST_KEY_MAP);
    instructions.put(157, OpName.BUILD_STRING);
    instructions.put(158, OpName.BUILD_TUPLE_UNPACK_WITH_CALL);
    instructions.put(160, OpName.LOAD_METHOD);
    instructions.put(161, OpName.CALL_METHOD);
    instructions.put(162, OpName.CALL_FINALLY);
    instructions.put(163, OpName.POP_FINALLY);
  }

  public enum OpName {
    POP_TOP,
    ROT_TWO,
    ROT_THREE,
    DUP_TOP,
    DUP_TOP_TWO,
    ROT_FOUR,
    NOP,
    UNARY_POSITIVE,
    UNARY_NEGATIVE,
    UNARY_NOT,
    UNARY_INVERT,
    BINARY_MATRIX_MULTIPLY,
    INPLACE_MATRIX_MULTIPLY,
    BINARY_POWER,
    BINARY_MULTIPLY,
    BINARY_MODULO,
    BINARY_ADD,
    BINARY_SUBTRACT,
    BINARY_SUBSCR,
    BINARY_FLOOR_DIVIDE,
    BINARY_TRUE_DIVIDE,
    INPLACE_FLOOR_DIVIDE,
    INPLACE_TRUE_DIVIDE,
    GET_AITER,
    GET_ANEXT,
    BEFORE_ASYNC_WITH,
    BEGIN_FINALLY,
    END_ASYNC_FOR,
    INPLACE_ADD,
    INPLACE_SUBTRACT,
    INPLACE_MULTIPLY,
    INPLACE_MODULO,
    STORE_SUBSCR,
    DELETE_SUBSCR,
    BINARY_LSHIFT,
    BINARY_RSHIFT,
    BINARY_AND,
    BINARY_XOR,
    BINARY_OR,
    INPLACE_POWER,
    GET_ITER,
    GET_YIELD_FROM_ITER,
    PRINT_EXPR,
    LOAD_BUILD_CLASS,
    YIELD_FROM,
    GET_AWAITABLE,
    INPLACE_LSHIFT,
    INPLACE_RSHIFT,
    INPLACE_AND,
    INPLACE_XOR,
    INPLACE_OR,
    WITH_CLEANUP_START,
    WITH_CLEANUP_FINISH,
    RETURN_VALUE,
    IMPORT_STAR,
    SETUP_ANNOTATIONS,
    YIELD_VALUE,
    POP_BLOCK,
    END_FINALLY,
    POP_EXCEPT,
    STORE_NAME,
    DELETE_NAME,
    UNPACK_SEQUENCE,
    FOR_ITER,
    UNPACK_EX,
    STORE_ATTR,
    DELETE_ATTR,
    STORE_GLOBAL,
    DELETE_GLOBAL,
    LOAD_CONST,
    LOAD_NAME,
    BUILD_TUPLE,
    BUILD_LIST,
    BUILD_SET,
    BUILD_MAP,
    LOAD_ATTR,
    COMPARE_OP,
    IMPORT_NAME,
    IMPORT_FROM,
    JUMP_FORWARD,
    JUMP_IF_FALSE_OR_POP,
    JUMP_IF_TRUE_OR_POP,
    JUMP_ABSOLUTE,
    POP_JUMP_IF_FALSE,
    POP_JUMP_IF_TRUE,
    LOAD_GLOBAL,
    SETUP_FINALLY,
    LOAD_FAST,
    STORE_FAST,
    DELETE_FAST,
    RAISE_VARARGS,
    CALL_FUNCTION,
    MAKE_FUNCTION,
    BUILD_SLICE,
    LOAD_CLOSURE,
    LOAD_DEREF,
    STORE_DEREF,
    DELETE_DEREF,
    CALL_FUNCTION_KW,
    CALL_FUNCTION_EX,
    SETUP_WITH,
    EXTENDED_ARG,
    LIST_APPEND,
    SET_ADD,
    MAP_ADD,
    LOAD_CLASSDEREF,
    BUILD_LIST_UNPACK,
    BUILD_MAP_UNPACK,
    BUILD_MAP_UNPACK_WITH_CALL,
    BUILD_TUPLE_UNPACK,
    BUILD_SET_UNPACK,
    SETUP_ASYNC_WITH,
    FORMAT_VALUE,
    BUILD_CONST_KEY_MAP,
    BUILD_STRING,
    BUILD_TUPLE_UNPACK_WITH_CALL,
    LOAD_METHOD,
    CALL_METHOD,
    CALL_FINALLY,
    POP_FINALLY
  }
}
