import io
import time
print("Python开始运行")
#temp = ("a.txt", "r")
fr = io.open("a.txt", 'r')
ft = io.open("c.txt", 'r+')

#io.readLines(ft)
io.write(ft,"12345")
io.readline(ft)
io.write(ft,"67890")
#lines=io.readLines(fr,5)

io.close(fr)
io.closed(fr)
io.close(ft)
io.closed(ft)


#io.fileno(fr)
#io.write(fr,"write test ")
#io.test(fr)
#io.read(fr)


#io.readline(fr)
#io.readline(fr)
#io.readline(fr,5)
#io.close(fr)
#print(io.closed(fr))
#fr=io.close(fr)
#print(io.closed(fr))

'''
io.readline(fr)
io.readlines(fr,3)
print(fr)
io.close(fr)
print(io.closed(fr))
print(time.time())
'''
