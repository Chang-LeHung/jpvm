def hello(a=1, b=2, c=3, *agrs, d=10, **kwargs):
    print(a, b, c, agrs, d, kwargs)
    print(a, b, c, agrs)


hello(4, 5, 6, 6, 7, 100, k=100)
