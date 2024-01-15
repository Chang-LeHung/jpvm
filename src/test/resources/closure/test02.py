def test_co02():
    a = 1
    b = 2

    def g():
        return a + b

    return a + b + g()


print(test_co02())
