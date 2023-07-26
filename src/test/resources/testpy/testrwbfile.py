# f=open("src/test/resources/testpy/test.bin", "rb")
data = b'\x48\x65\x6c\x6c\x6f\x20\x57\x6f\x72\x6c\x64'
f=open("src/test/resources/testpy/testwb.bin", "wb")
f.write(data)
# bd = f.read()
# print(bd)