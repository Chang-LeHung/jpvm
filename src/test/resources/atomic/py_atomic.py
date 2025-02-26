
import threading
import time

val = 0
lock = threading.RLock()


def func():
    global val
    for i in range(10000):
        lock.acquire()
        val += 1
        lock.release()

threads = []
for i in range(100):
    threads.append(threading.Thread(target=func))
start = time.time()
for t in threads:
    t.start()

for t in threads:
    t.join()
end = time.time()
print(val)
print(end - start)
