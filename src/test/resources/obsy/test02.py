class MultiplicationTable:
    def __init__(self, n):
        self.n = n

    def print_table(self):
        for i in range(1, self.n + 1):
            for j in range(1, i + 1):
                print(f"{j} × {i} = {i * j}\t", end="")
            print()


# 使用示例
table = MultiplicationTable(9)
table.print_table()
