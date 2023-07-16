import threading

# 创建锁
lock = threading.Lock()

# 共享资源
shared_resource = 0


# 线程函数
def increment():
    global shared_resource
    for _ in range(1000000):
        # 获取锁
        lock.acquire()
        try:
            shared_resource += 1
        finally:
            # 释放锁
            lock.release()


# 创建两个线程
thread1 = threading.Thread(target=increment)
thread2 = threading.Thread(target=increment)

# 启动线程
thread1.start()
thread2.start()

# 等待线程结束
thread1.join()
thread2.join()

# 打印最终结果
print("Final value of shared_resource:", shared_resource)
