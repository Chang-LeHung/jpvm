
class A:
    def __init__(self, n, s):
        self.n = n
        self.s = s

    def __sub__(self, other):
        res = []
        for i in range(len(self.n)):
            res.append(self.n[i] - other.n[i])
        return res

    def __mul__(self, other):
        return self.s * self.s

    def __mod__(self, other):
        return self.s % other.s


if __name__ == '__main__':
    a = A([1,2,3], 5)
    b = A([4,5,6], 9)
    subres = b - a
    print(subres)
    mulres = a * b
    print(mulres)
    modres = b % a
    print(modres)
