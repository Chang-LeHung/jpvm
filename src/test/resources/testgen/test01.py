def fibonacci():
    yield 1
    yield 2
    yield 3
    yield 4


# 使用生成器函数生成斐波那契数列
fib = fibonacci()
print(next(fib))
print(next(fib))
print(next(fib))
print(next(fib))
print(next(fib))
