def counter():
    i = 0
    while True:
        value = yield i
        if value is not None:
            i = value
        else:
            i += 1


# def test():
#     for i in range(10):
#         yield i
#

def call():
    # 创建生成器
    gen = counter()

    # 调用next()迭代生成器至第一个yield表达式处，并返回0
    print(next(gen))  # 输出: 0

    # 使用send()发送值并接收生成器的下一个yield表达式的返回值
    print(gen.send(5))  # 输出: 5

    # 继续迭代生成器，返回6（因为上一次send()发送了5）
    print(next(gen))  # 输出: 6

    # 使用send()发送None，生成器会继续自增并返回下一个值
    print(gen.send(None))  # 输出: 7

    # 继续迭代生成器，返回8（因为上一次send()发送了None）
    print(next(gen))  # 输出: 8


t = call()
