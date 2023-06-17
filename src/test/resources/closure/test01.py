def outer_function():
    count = 0

    def inner_function():
        nonlocal count
        count += 1
        print(count)

    return inner_function

# 创建闭包
closure = outer_function()

# 调用闭包多次，每次会修改外部函数中的 count 变量
closure()  # 输出 1
closure()  # 输出 2
closure()  # 输出 3

print(closure)
