import time


class MetaClass(type):

    def __new__(cls, name, bases, dct):
        print("In MetaClass")
        return super().__new__(cls, name, bases, dct)


class A(metaclass=MetaClass):

    def __init__(self, arg):
        self.arg = arg

    def serve_forever(self):
        while True:
            self.method()
            time.sleep(self.arg)


class B(A):

    def method(self):
        print('bar methoding')


bar = B(arg=5)


class C(A):

    def method(self):
        print('foo methoding')


foo = C(arg=5)


class D(A):

    def run(self):
        print('xxxx')


fault = D(arg=5)
