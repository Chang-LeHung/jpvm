class A(object):
    for i in range(10):
        print("hello world")

    def __init__(self):
        self.name = "hello world"

    def hello(self):
        print("helloworld")

    def __add__(self, other):
        pass


a = A()
print(a.name)
print(A)
print(type(A))
a.hello()
