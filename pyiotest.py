import io
import time
print("Python开始运行")
#temp = ("a.txt", "r")
fr = io.open("a.txt", 'r')
ft = io.open("b.txt", 'r')

io.readable(fr)
io.readLines(fr,5)
io.readable(fr)
io.close(fr)
io.closed(fr)
io.readable(fr)
io.close(ft)
io.closed(ft)


#io.fileno(fr)
#io.write(fr,"write test ")
#io.test(fr)
#io.read(fr)


#io.readLine(fr)
#io.readLine(fr)
#io.readLine(fr,5)
#io.close(fr)
#print(io.closed(fr))
#fr=io.close(fr)
#print(io.closed(fr))

'''
io.readLine(fr)
io.readLines(fr,3)
print(fr)
io.close(fr)
print(io.closed(fr))
print(time.time())
'''
