def fibonacci(n):
    if n <= 0:
        return 0
    elif n == 1:
        return 0
    elif n == 2:
        return 1
    else:
        return fibonacci(n - 1) + fibonacci(n - 2)


# 示例：计算斐波那契数列中的第10个数
n = 10
fib_number = fibonacci(n)
print(fib_number)
