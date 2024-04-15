#测试读
import io
fw=io.open("test02.txt", 'wb')
ww=["test\n",'999\n','\n','777\n','666']
io.writelines(fw,ww)
io.close(fw)

#r模式
fr = io.open("test02.txt", 'r')
temp=io.readlines(fr,1)
if temp==['test']:pass
else:print("r readlines readline error")
#print("readlines1",temp)#['test']
fr = io.open("test02.txt", 'r')
temp=io.readlines(fr,7)
if temp==['test', '999', '', '777', '666']:pass
else:print("r readlines overline read error")
#print("readlines7",temp)#['test', '999', '', '777', '666']
io.close(fr)
fr = io.open("test02.txt", 'r')
temp=io.readlines(fr)
if temp==["test\n",'999\n','\n','777\n','666']:pass
else:print("r readlines readall error")
#print("readlinesall",temp)["test\n",'999\n','\n','777\n','666']
io.close(fr)
fr = io.open("test02.txt", 'r')
temp=io.readline(fr,3)
if temp=="tes":pass
else:print("r readline read3error")
#print("read3",temp)#tes
io.close(fr)
fr = io.open("test02.txt", 'r')
temp=io.readline(fr)
if temp=="test":pass
else:print("r readline error")
#print("readall",temp)#test
io.close(fr)

#rb模式
frb = io.open("test02.txt", 'rb')
temp=io.readlines(frb,1)
if temp==['test\n']:pass
else:print("rb readlines readline error")
#print("readlines1",temp)#['test']
frb = io.open("test02.txt", 'rb')
temp=io.readlines(frb,7)
if temp==["test\n",'999\n','\n','777\n','666']:pass
else:print("rb readlines overline read error")
#print("readlines7",temp)#['test', '999', '', '777', '666']
io.close(frb)
frb = io.open("test02.txt", 'rb')
temp=io.readlines(frb)
if temp==["test\n",'999\n','\n','777\n','666']:pass
else:print("rb readlines readall error")
#print("readlinesall",temp)#["test\n",'999\n','\n','777\n','666']
io.close(frb)
frb = io.open("test02.txt", 'rb')
temp=io.readline(frb,3)
if temp=="tes":pass
else:print("rb readline read3error")
#print("read3",temp)#tes
io.close(frb)
frb = io.open("test02.txt", 'rb')
temp=io.readline(frb)
if temp=="test\n":pass
else:print("rb readline error")
#print("readall",temp)#test
io.close(frb)

#r+模式
frr = io.open("test02.txt", 'r+')
temp=io.readlines(frr,1)
if temp==['test']:pass
else:print("r+ readlines readline error")
#print("readlines1",temp)#['test']
frr = io.open("test02.txt", 'r+')
temp=io.readlines(frr,7)
if temp==['test', '999', '', '777', '666']:pass
else:print("r+ readlines overline read error")
#print("readlines7",temp)#['test', '999', '', '777', '666']
io.close(frr)
frr = io.open("test02.txt", 'r+')
temp=io.readlines(frr)
if temp==["test\n",'999\n','\n','777\n','666']:pass
else:print("r+ readlines readall error")
#print("readlinesall",temp)["test\n",'999\n','\n','777\n','666']
io.close(frr)
frr = io.open("test02.txt", 'r+')
temp=io.readline(frr,3)
if temp=="tes":pass
else:print("r+ readline read3error")
#print("read3",temp)#tes
io.close(frr)
frr = io.open("test02.txt", 'r+')
temp=io.readline(frr)
if temp=="test":pass
else:print("r+ readline error")
#print("readall",temp)#test
io.close(frr)

#rb+模式
frbr = io.open("test02.txt", 'rb+')
temp=io.readlines(frbr,1)
if temp==['test\n']:pass
else:print("rb+ readlines readline error")
#print("readlines1",temp)#['test']
frbr = io.open("test02.txt", 'rb+')
temp=io.readlines(frbr,7)
if temp==["test\n",'999\n','\n','777\n','666']:pass
else:print("rb+ readlines overline read error")
#print("readlines7",temp)#['test', '999', '', '777', '666']
io.close(frbr)
frbr = io.open("test02.txt", 'rb+')
temp=io.readlines(frbr)
if temp==["test\n",'999\n','\n','777\n','666']:pass
else:print("rb+ readlines readall error")
#print("readlinesall",temp)#["test\n",'999\n','\n','777\n','666']
io.close(frbr)
frbr = io.open("test02.txt", 'rb+')
temp=io.readline(frbr,3)
if temp=="tes":pass
else:print("rb+ readline read3error")
#print("read3",temp)#tes
io.close(frbr)
frbr = io.open("test02.txt", 'rb+')
temp=io.readline(frbr)
if temp=="test\n":pass
else:print("rb+ readline error")
#print("readall",temp)#test
io.close(frbr)
