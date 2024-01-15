def hello():
    a = 1 / 0
    yield 1


if __name__ == '__main__':
    f = hello()
    # print(next(f))
    data = [1, 2, 3]
    print(data[10])
