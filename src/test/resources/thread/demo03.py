import threading
import time

data = []


def worker():
    for i in range(10):
        data.append(i)
        time.sleep(0.01)
        print(data)


t1 = threading.Thread(target=worker)
t2 = threading.Thread(target=worker)
t1.start()
t2.start()
t1.join()
t2.join()
print(data)
