package org.jpvm.pvm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GILRuntimeState {
  private final long interval;
  private final AtomicBoolean locked;
  private final ReentrantLock lock;
  private final Condition condition;
  private volatile boolean dropGILRequest;
  /**
   * purpose of use a1-a8/b1-b8/c1-c8 is to avoid false sharing
   */
  private volatile long a1, a2, a3, a4, a5, a6, a7, a8;
  private volatile long b1, b2, b3, b4, b5, b6, b7, b8;
  private volatile long switchNumber;
  private volatile long c1, c2, c3, c4, c5, c6, c7, c8;
  private volatile Thread lastHolder;

  public GILRuntimeState(long interval) {
    this.interval = interval;
    lock = new ReentrantLock();
    condition = lock.newCondition();
    switchNumber = 0;
    locked = new AtomicBoolean();
  }

  public long getInterval() {
    return interval;
  }


  public boolean isLocked() {
    return locked.get();
  }

  public long getSwitchNumber() {
    return switchNumber;
  }

  public void setSwitchNumber(long switchNumber) {
    this.switchNumber = switchNumber;
  }

  public Thread getLastHolder() {
    return lastHolder;
  }

  public boolean isDropGILRequest() {
    return dropGILRequest;
  }

  public void takeGIL() {
    try {
      lock.lock();
      for (; ; ) {
        while (locked.get()) {
          long saveSwitchNumber = switchNumber;
          long start = System.currentTimeMillis();
          try {
            condition.await(interval, TimeUnit.MILLISECONDS);
          } catch (InterruptedException ignored) {
            // do nothing
          }
          long end = System.currentTimeMillis();
          if ((end - start) >= interval
              && locked.get() && !dropGILRequest
              && saveSwitchNumber == switchNumber) {
            dropGILRequest = true;
          }
        }
        // many threads can reach here at the same time
        // so below code is critical section
        // avoid data race by using atomic operations
        if (locked.compareAndSet(false, true)) {
          dropGILRequest = false;
          break;
        }
      }
    } finally {
      lock.unlock();
    }
  }

  public void dropGIL() {
    try {
      lock.lock();
      locked.set(false);
      lastHolder = Thread.currentThread();
      condition.signal();
    } finally {
      lock.unlock();
    }
  }
}
