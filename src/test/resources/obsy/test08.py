class Person:
    def __init__(self, name, age):
        super().__init__()
        self.name = name
        self.age = age

    def say_hello(self):
        print(f"Hello, my name is {self.name} and I am {self.age} years old.")


# 创建一个 Person 实例
person = Person("Alice", 25)

# 调用实例方法
person.say_hello()
