def hello():
    a = 1 / 0


def b():
    hello()


def a():
    b()


a()
