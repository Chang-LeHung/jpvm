import io
import time
print("Python开始运行")
#temp = ("a.txt", "r")
fr = io.open("a.txt", "rr")
#io.write(fr,"write test ")
io.read(fr)
io.readLine(fr)
io.readLines(fr,3)
print(fr)
io.close(fr)
print(io.closed(fr))
print(time.time())
