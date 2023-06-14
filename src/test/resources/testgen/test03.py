def generator1():
    yield 1


def generator2():
    yield from generator1()
    yield 'Done'


f = generator2()
# print(next(f))
# print(f.send(2))
for i in f:
    print(i)

