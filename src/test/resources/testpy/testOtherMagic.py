class Number:
    def __init__(self, value):
        self.value = value

    def __iadd__(self, other):
        self.value += other
        return self

    def __isub__(self, other):
        self.value -= other
        return self

    def __imul__(self, other):
        self.value *= other
        return self

    def __itruediv__(self, other):
        self.value /= other
        return self

    def __imod__(self, other):
        self.value %= other
        return self

# 创建一个Number对象
a = Number(5)

# 在加法表达式中使用Number对象
a += 10
print(a.value)
a -= 3
print(a.value)
a *=2
print(a.value)
a %= 5
print(a.value)
a /= 2
print(a.value)