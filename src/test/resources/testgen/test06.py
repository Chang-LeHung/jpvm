def hello():
    res = yield 1
    print("res = ", res)
    res = yield 2
    if res < 10:
        print("less than 10")
    else:
        print("greater than or equal to 10")


def world():
    yield from hello()


o = world()
print(next(o))
print(o.send(1024))
print(o.send(4096))
