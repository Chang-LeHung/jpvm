class Demo(object):

    def __init__(self, *args, **kwargs):
        super().__init__()
        print(f"__init__ {args = } {kwargs = }")

    def __call__(self, *args, **kwargs):
        print("call")

    def __new__(cls, *args, **kwargs):
        print(f"__new__ {args = } {kwargs = }")
        return super().__new__(cls)


if __name__ == '__main__':
    print(Demo(1, 2, 3, 4, a=1, b=2))
