package org.jpvm.pycParser;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BinaryUtil {

  public static int nextInt(FileInputStream stream) throws IOException {
    var bytes = new byte[4];
    var size = stream.read(bytes);
    assert size == 4;
    // 指定数据存储方式为小端方式
    return ByteBuffer.wrap(bytes).order(LITTLE_ENDIAN).getInt();
  }
}
