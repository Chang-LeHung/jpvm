#测试读写
import io

#"r+":
fw=io.open("test04.txt", 'w')
ww=["123456789"]
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test04.txt", 'r+')
ww=["abc"]
io.writelines(fr,ww)
temp=io.readlines(fr)
if temp==["456789"]:pass
else:print("r+ RW write error")
#print("readlines",temp)
io.seek(fr,1,0)
temp=io.readlines(fr)
if temp==["bc456789"]:pass
else:print("r+ RW seek0 error")
io.seek(fr,1,0)
io.seek(fr,1,1)
temp=io.readlines(fr)
if temp==['c456789']:pass
else:print("r+ RW seek1 error")
#print("readlines",temp)
io.seek(fr,-1,2)
temp=io.readlines(fr)
if temp==['9']:pass
else:print("r+ RW seek2 error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["abc456789"]:pass
else:print("r+ RW error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r+')
ww="123"
io.write(fr,ww)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["123456789"]:pass
else:print("r+ write error")
#print("readlines",temp)
io.close(fr)

#"w+":覆盖原文件
fw=io.open("test04.txt", 'w')
ww=["123456789"]
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test04.txt", 'w+')
ww=["abc"]
io.writelines(fr,ww)
temp=io.readlines(fr)
if temp==['']:pass
else:print("w+ RW write error")
#print("readlines",temp)
io.seek(fr,1,0)
temp=io.readlines(fr)
if temp==["bc"]:pass
else:print("w+ RW seek0 error")
io.seek(fr,1,0)
io.seek(fr,1,1)
temp=io.readlines(fr)
if temp==['c']:pass
else:print("w+ RW seek1 error")
io.seek(fr,-1,2)
temp=io.readlines(fr)
if temp==['c']:pass
else:print("w+ RW seek2 error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["abc"]:pass
else:print("w+ RW error")
#print("readlines",temp)
io.close(fr)

#"a+":
fw=io.open("test04.txt", 'w')
ww=["123456789"]
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test04.txt", 'a+')
ww=["abc"]
io.writelines(fr,ww)
temp=io.readlines(fr)
if temp==['']:pass
else:print("a+ RW write error")
#print("readlines",temp)
io.seek(fr,1,0)
temp=io.readlines(fr)
if temp==["23456789abc"]:pass
else:print("a+ RW seek0 error")
io.seek(fr,1,0)
io.seek(fr,1,1)
temp=io.readlines(fr)
if temp==['3456789abc']:pass
else:print("a+ RW seek1 error")
io.seek(fr,-1,2)
temp=io.readlines(fr)
if temp==['c']:pass
else:print("a+ RW seek2 error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["123456789abc"]:pass
else:print("a+ RW error")
#print("readlines",temp)
io.close(fr)

#"rb+":
fw=io.open("test04.txt", 'w')
ww=["123456789"]
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test04.txt", 'rb+')
ww=["abc"]
io.writelines(fr,ww)
temp=io.readlines(fr)
if temp==["456789"]:pass
else:print("rb+ RW write error")
#print("readlines",temp)
io.seek(fr,1,0)
temp=io.readlines(fr)
if temp==["bc456789"]:pass
else:print("rb+ RW seek0 error")
io.seek(fr,1,0)
io.seek(fr,1,1)
temp=io.readlines(fr)
if temp==['c456789']:pass
else:print("rb+ RW seek1 error")
#print("readlines",temp)
io.seek(fr,-1,2)
temp=io.readlines(fr)
if temp==['9']:pass
else:print("rb+ RW seek2 error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["abc456789"]:pass
else:print("rb+ RW error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'rb+')
ww="123"
io.write(fr,ww)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["123456789"]:pass
else:print("rb+ write error")
#print("readlines",temp)
io.close(fr)

#"wb+":
fw=io.open("test04.txt", 'w')
ww=["123456789"]
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test04.txt", 'wb+')
ww=["abc"]
io.writelines(fr,ww)
temp=io.readlines(fr)
if temp==['']:pass
else:print("wb+ RW write error")
#print("readlines",temp)
io.seek(fr,1,0)
temp=io.readlines(fr)
if temp==["bc"]:pass
else:print("wb+ RW seek0 error")
io.seek(fr,1,0)
io.seek(fr,1,1)
temp=io.readlines(fr)
if temp==['c']:pass
else:print("wb+ RW seek1 error")
io.seek(fr,-1,2)
temp=io.readlines(fr)
if temp==['c']:pass
else:print("wb+ RW seek2 error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["abc"]:pass
else:print("wb+ RW error")
#print("readlines",temp)
io.close(fr)

#"ab+":
fw=io.open("test04.txt", 'w')
ww=["123456789"]
io.writelines(fw,ww)
io.close(fw)
fr=io.open("test04.txt", 'ab+')
ww=["abc"]
io.writelines(fr,ww)
temp=io.readlines(fr)
if temp==['']:pass
else:print("ab+ RW write error")
#print("readlines",temp)
io.seek(fr,1,0)
temp=io.readlines(fr)
if temp==["23456789abc"]:pass
else:print("ab+ RW seek0 error")
io.seek(fr,1,0)
io.seek(fr,1,1)
temp=io.readlines(fr)
if temp==['3456789abc']:pass
else:print("ab+ RW seek1 error")
io.seek(fr,-1,2)
temp=io.readlines(fr)
if temp==['c']:pass
else:print("ab+ RW seek2 error")
#print("readlines",temp)
io.close(fr)
fr=io.open("test04.txt", 'r')
temp=io.readlines(fr)
if temp==["123456789abc"]:pass
else:print("ab+ RW error")
#print("readlines",temp)
io.close(fr)
