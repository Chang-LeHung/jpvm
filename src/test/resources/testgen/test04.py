def generator1():
    a = yield 1
    print(f"{a = }")


def generator2():
    yield from generator1()
    yield 'Done'


f = generator2()
print(next(f))
f.send(100)
