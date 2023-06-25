class A:

    def __init__(self):
        self.n = [1, 2, 3, 4, 5]

    def __add__(self, other):
        res = []
        for i in range(len(self.n)):
            res.append(self.n[i] + other.n[i])
        return res


if __name__ == '__main__':
    a = A()
    c = a + a
    print(c)
