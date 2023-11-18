def hello(*args, **kwargs):
    print(args)
    print(kwargs)


hello(1, 2, 3, 4, a=1, b=2)
