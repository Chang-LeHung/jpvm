package org.jpvm.excptions;

/** this class save pvm execution state machine : - stack / stack size - execution point */
public class TryBlockHandler {

  public static int EXCEPT_HANDLER = 257;
  public static int SETUP_FINALLY = 122;
  private int type;
  private int handler;
  private int level;

  public TryBlockHandler(int type, int handler, int level) {
    this.type = type;
    this.handler = handler;
    this.level = level;
  }

  public TryBlockHandler() {}

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getHandler() {
    return handler;
  }

  public void setHandler(int handler) {
    this.handler = handler;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
