class Animal:
    def __init__(self, name):
        self.name = name

    def sound(self):
        pass


class Dog(Animal):

    # def __init__(self, name):
    #     super(Dog, self).__init__(name)

    def sound(self):
        print("Woof!")


class Cat(Animal):

    def __init__(self, name):
        super(Cat, self).__init__(name)

    def sound(self):
        print("Meow!")


# 创建一个 Dog 实例
dog = Dog("Buddy")
dog.sound()  # 输出: Woof!
print(dog.name)  # 输出: Buddy

# 创建一个 Cat 实例
cat = Cat("Kitty")
cat.sound()  # 输出: Meow!
print(cat.name)  # 输出: Kitty
