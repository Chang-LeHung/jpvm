import random
import threading


def generate_matrix(rows, columns):
    matrix = []
    for _ in range(rows):
        row = []
        for _ in range(columns):
            row.append(random.randint(1, 10))
        matrix.append(row)
    return matrix


def multiply(i, columns1, columns2, result, matrix1, matrix2):
    tmp = [[0 for _ in range(columns2)] for _ in range(columns1)]
    for j in range(columns2):
        for k in range(columns1):
            tmp[i][j] += matrix1[i][k] * matrix2[k][j]
    for j in range(columns2):
        for k in range(columns1):
            result[i][j] += tmp[i][j]


def matrix_multiply(matrix1, matrix2):
    rows1 = len(matrix1)
    columns1 = len(matrix1[0])
    rows2 = len(matrix2)
    columns2 = len(matrix2[0])

    if columns1 != rows2:
        return None

    result = [[0 for _ in range(columns2)] for _ in range(rows1)]

    threads = []
    for i in range(rows1):
        t = threading.Thread(target=multiply, args=(i, columns1, columns2, result, matrix1, matrix2))
        t.start()
        threads.append(t)
    for t in threads:
        t.join()
    return result


matrix1 = generate_matrix(2, 2)
matrix2 = generate_matrix(2, 2)

import time

start = time.time()
result = matrix_multiply(matrix1, matrix2)
end = time.time()
print(end - start)

print("matrix 1")
for row in matrix1:
    print(row)
print("matrix 2")
for row in matrix2:
    print(row)

print("matrix 1 * matrix 2")
for row in result:
    print(row)
