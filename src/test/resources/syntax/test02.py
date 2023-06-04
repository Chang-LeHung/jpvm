x = 5
if x > 0:
    print("x 是正数")
elif x == 0:
    print("x 是零")
else:
    print("x 是负数")


def hello():
    print(hello.__globals__)
    print(x)
print(locals())
print(globals())
print(locals() == globals())
print(locals() is globals())
hello()