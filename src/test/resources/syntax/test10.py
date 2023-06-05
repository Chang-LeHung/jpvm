def calculate_operations(a, b):
    addition = a + b
    subtraction = a - b
    multiplication = a * b
    division = a / b
    modulus = a % b
    logical_and = bool(a) and bool(b)
    logical_or = bool(a) or bool(b)
    xor = a ^ b

    return addition, subtraction, multiplication, division, modulus, logical_and, logical_or, xor

# 示例：对给定数据执行操作
a = 10
b = 4
results = calculate_operations(a, b)
print(results)
