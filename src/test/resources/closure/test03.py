def hello(i, j):
    data = [[t for t in range(i)] for _ in range(j)]
    print(data)


if __name__ == '__main__':
    hello(5, 8)
