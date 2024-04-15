#测试写
import io
#w模式
fw=io.open("test03.txt", 'w')
ww=["test\n", '','999','\n','888\n','777']
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n",'999\n','888\n','777']:pass
else:print("w writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#a模式
fw=io.open("test03.txt", 'w')
ww=["test\n", '','999','\n','888\n']
io.writelines(fw,ww)
io.close(fw)
fa=io.open("test03.txt", 'a')
ww=["test\n", '','999','\n','888\n','666']
io.writelines(fa,ww)
io.close(fa)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n", '999\n','888\n',"test\n",'999\n','888\n','666']:pass
else:print("a writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#wb模式
fw=io.open("test03.txt", 'wb')
ww=["test\n", '','999','\n','888\n','666']
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n",'999\n','888\n','666']:pass
else:print("wb writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#ab模式
fw=io.open("test03.txt", 'w')
ww=["test\n", '','999','\n','888\n']
io.writelines(fw,ww)
io.close(fw)
fa=io.open("test03.txt", 'ab')
ww=["test\n", '','999','\n','888\n','666']
io.writelines(fa,ww)
io.close(fa)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n", '999\n','888\n',"test\n",'999\n','888\n','666']:pass
else:print("ab writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#w+模式
fw=io.open("test03.txt", 'w+')
ww=["test\n", '','999','\n','888\n','555']
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n",'999\n','888\n','555']:pass
else:print("w+ writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#a+模式
fw=io.open("test03.txt", 'w')
ww=["test\n", '','999','\n','888\n']
io.writelines(fw,ww)
io.close(fw)
fa=io.open("test03.txt", 'a+')
ww=["test\n", '','999','\n','888\n','666']
io.writelines(fa,ww)
io.close(fa)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n", '999\n','888\n',"test\n",'999\n','888\n','666']:pass
else:print("a+ writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#Wb+模式
fw=io.open("test03.txt", 'wb+')
ww=["test\n", '','999','\n','888\n','444']
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n",'999\n','888\n','444']:pass
else:print("wb+ writelines readall error")
#print("readlinesall",temp)
io.close(fr)

#ab+模式
fw=io.open("test03.txt", 'w')
ww=["test\n", '','999','\n','888\n']
io.writelines(fw,ww)
io.close(fw)
fa=io.open("test03.txt", 'ab+')
ww=["test\n", '','999','\n','888\n','666']
io.writelines(fa,ww)
io.close(fa)
fr=io.open("test03.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n", '999\n','888\n',"test\n",'999\n','888\n','666']:pass
else:print("ab+ writelines readall error")
#print("readlinesall",temp)
io.close(fr)