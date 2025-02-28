
import atomic
import threading
import time

val = atomic.AtomicLong(0)


def func():
    global val
    for i in range(10000):
        val += 1

threads = []
for i in range(100):
    threads.append(threading.Thread(target=func))
start = time.time()
for t in threads:
    t.start()

for t in threads:
    t.join()
end = time.time()
print(val.get())
print(end - start)