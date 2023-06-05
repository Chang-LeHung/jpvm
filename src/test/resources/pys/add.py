def add(a, b=1, c=2):
    """hello add"""
    d = 2
    g = a * d
    return g + b


def sub(a, b, /, c, d, *, h=1, w=2):
    """hello world"""
    pass


data = add(1, b=2)
print(data)
