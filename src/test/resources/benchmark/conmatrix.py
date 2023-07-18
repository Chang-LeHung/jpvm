import random
import threading


def generate_matrix(rows, columns):
    matrix = []
    for _ in range(rows):
        row = []
        for _ in range(columns):
            # 生成随机整数作为矩阵元素
            row.append(random.randint(1, 10))
        matrix.append(row)
    return matrix


def multiply(i, columns1, columns2, result, matrix1, matrix2):
    for j in range(columns2):
        for k in range(columns1):
            result[i][j] += matrix1[i][k] * matrix2[k][j]


def matrix_multiply(matrix1, matrix2):
    rows1 = len(matrix1)
    columns1 = len(matrix1[0])
    rows2 = len(matrix2)
    columns2 = len(matrix2[0])

    # 检查矩阵能够相乘
    if columns1 != rows2:
        return "无法进行矩阵乘法"

    # 创建结果矩阵
    result = [[0 for _ in range(columns2)] for _ in range(rows1)]

    # 矩阵乘法
    threads = []
    for i in range(rows1):
        t = threading.Thread(target=multiply, args=(i, columns1, columns2, result, matrix1, matrix2))
        t.start()
        threads.append(t)
    for t in threads:
        t.join()
    return result


# 生成两个矩阵
matrix1 = generate_matrix(16, 800)
matrix2 = generate_matrix(800, 80000)

import time

start = time.time()
result = matrix_multiply(matrix1, matrix2)
end = time.time()
print(end - start)
