#创建一个元组
my_tuple = (1, 2, 3, 4, 5)

# 使用元组方法进行测试

# 访问元组元素
print(my_tuple[0])  # 输出: 1

# 获取元组的长度
print(len(my_tuple))  # 输出: 5

# 元组切片
print(my_tuple[1:3])  # 输出: (2, 3)

# 元组连接
other_tuple = (6, 7, 8)
combined_tuple = my_tuple + other_tuple
print(combined_tuple)  # 输出: (1, 2, 3, 4, 5, 6, 7, 8)

# 元组重复
repeated_tuple = my_tuple * 2
print(repeated_tuple)  # 输出: (1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

# 元素是否存在于元组中
print(3 in my_tuple)  # 输出: True
print(6 in my_tuple)  # 输出: False

# 元素的索引
print(my_tuple.index(3))  # 输出: 2

# 元素出现的次数
print(my_tuple.count(4))  # 输出: 1

# 遍历元组元素
for item in my_tuple:
    print(item)  # 逐行输出: 1, 2, 3, 4, 5
# 元组解包
a, b, c, d, e = my_tuple
print(a, b, c, d, e)  # 输出: 1 2 3 4 5

# 元组转换为列表
my_list = list(my_tuple)
print(my_list)  # 输出: [1, 2, 3, 4, 5]

# 判断元组是否为空
empty_tuple = ()
print(len(empty_tuple) == 0)  # 输出: True

# 元组排序
unsorted_tuple = (4, 1, 3, 5, 2)
sorted_tuple = tuple(sorted(unsorted_tuple))
print(sorted_tuple)  # 输出: (1, 2, 3, 4, 5)

# 比较元组
tuple1 = (1, 2, 3)
tuple2 = (4, 5, 6)
print(tuple1 < tuple2)  # 输出: True

# 元组的最大值和最小值
print(max(my_tuple))  # 输出: 5
print(min(my_tuple))  # 输出: 1

# 将可迭代对象转换为元组
my_string = "Hello"
converted_tuple = tuple(my_string)
print(converted_tuple)  # 输出: ('H', 'e', 'l', 'l', 'o')

print("-----------------测试元组比小于--------------")
# 数字元组
tuple1 = (1, 2, 3)
tuple2 = (4, 5, 6)
tuple3 = (1, 2, 3)

print(tuple1 < tuple2)  # 输出: True，因为 tuple1 的第一个元素 1 小于 tuple2 的第一个元素 4
print(tuple1 < tuple3)  # 输出: False，因为两个元组的所有元素都相等

# 字符串元组
tuple4 = ("apple", "banana")
tuple5 = ("cherry", "date")
tuple6 = ("apple", "banana")

print(tuple4 < tuple5)  # 输出: True，因为 tuple4 的第一个元素 "apple" 在字母表中小于 tuple5 的第一个元素 "cherry"
print(tuple4 < tuple6)  # 输出: False，因为两个元组的所有元素都相等

# 混合类型元组
tuple7 = (1, "apple")
tuple8 = (2, "banana")

print(tuple7 < tuple8)  # 输出: True，因为两个元组的第一个元素类型相同，并且 tuple7 的第一个元素 1 小于 tuple8 的第一个元素 2

# 元组长度不同
tuple9 = (1, 2, 3)
tuple10 = (1, 2)

print(tuple9 < tuple10)  # 输出: False，因为 tuple9 的长度较长

# 空元组
tuple11 = ()
tuple12 = (1, 2, 3)

print(tuple11 < tuple12)  # 输出: True，因为空元组被认为比任何非空元组小

print("----------------测试元组比大于---------------")
# 数字元组
tuple1 = (1, 2, 3)
tuple2 = (4, 5, 6)
tuple3 = (1, 2, 3)

print(tuple2 > tuple1)  # 输出: True，因为 tuple2 的第一个元素 4 大于 tuple1 的第一个元素 1
print(tuple3 > tuple1)  # 输出: False，因为两个元组的所有元素都相等

# 字符串元组
tuple4 = ("apple", "banana")
tuple5 = ("cherry", "date")
tuple6 = ("apple", "banana")

print(tuple5 > tuple4)  # 输出: True，因为 tuple5 的第一个元素 "cherry" 在字母表中大于 tuple4 的第一个元素 "apple"
print(tuple6 > tuple4)  # 输出: False，因为两个元组的所有元素都相等

# 混合类型元组
tuple7 = (1, "apple")
tuple8 = (2, "banana")

print(tuple8 > tuple7)  # 输出: True，因为两个元组的第一个元素类型相同，并且 tuple8 的第一个元素 2 大于 tuple7 的第一个元素 1

# 元组长度不同
tuple9 = (1, 2, 3)
tuple10 = (1, 2)

print(tuple9 > tuple10)  # 输出: True，因为 tuple9 的长度较长

# 空元组
tuple11 = ()
tuple12 = (1, 2, 3)

print(tuple12 > tuple11)  # 输出: True，因为非空元组被认为比任何空元组大

print("---------------------------测试元组小于等于--------------")
# 数字元组
tuple1 = (1, 2, 3)
tuple2 = (4, 5, 6)
tuple3 = (1, 2, 3)

print(tuple1 <= tuple2)  # 输出: True，因为 tuple1 的第一个元素 1 小于 tuple2 的第一个元素 4
print(tuple1 <= tuple3)  # 输出: True，因为两个元组的所有元素都相等

# 字符串元组
tuple4 = ("apple", "banana")
tuple5 = ("cherry", "date")
tuple6 = ("apple", "banana")

print(tuple4 <= tuple5)  # 输出: True，因为 tuple4 的第一个元素 "apple" 在字母表中小于 tuple5 的第一个元素 "cherry"
print(tuple4 <= tuple6)  # 输出: True，因为两个元组的所有元素都相等

# 混合类型元组
tuple7 = (1, "apple")
tuple8 = (2, "banana")

print(tuple7 <= tuple8)  # 输出: True，因为两个元组的第一个元素类型相同，并且 tuple7 的第一个元素 1 小于 tuple8 的第一个元素 2

# 元组长度不同
tuple9 = (1, 2, 3)
tuple10 = (1, 2)

print(tuple10 <= tuple9)  # 输出: True，因为 tuple10 的长度较短

# 空元组
tuple11 = ()
tuple12 = (1, 2, 3)

print(tuple11 <= tuple12)  # 输出: True，因为空元组被认为比任何非空元组小或相等

print("----------------测试元组大于等于-------------")

# 数字元组
tuple1 = (1, 2, 3)
tuple2 = (4, 5, 6)
tuple3 = (1, 2, 3)

print(tuple2 >= tuple1)  # 输出: True，因为 tuple2 的第一个元素 4 大于 tuple1 的第一个元素 1
print(tuple3 >= tuple1)  # 输出: True，因为两个元组的所有元素都相等

# 字符串元组
tuple4 = ("apple", "banana")
tuple5 = ("cherry", "date")
tuple6 = ("apple", "banana")

print(tuple5 >= tuple4)  # 输出: True，因为 tuple5 的第一个元素 "cherry" 在字母表中大于 tuple4 的第一个元素 "apple"
print(tuple6 >= tuple4)  # 输出: True，因为两个元组的所有元素都相等

# 混合类型元组
tuple7 = (1, "apple")
tuple8 = (2, "banana")

print(tuple8 >= tuple7)  # 输出: True，因为两个元组的第一个元素类型相同，并且 tuple8 的第一个元素 2 大于 tuple7 的第一个元素 1

# 元组长度不同
tuple9 = (1, 2, 3)
tuple10 = (1, 2)

print(tuple9 >= tuple10)  # 输出: True，因为 tuple9 的长度较长

# 空元组
tuple11 = ()
tuple12 = (1, 2, 3)

print(tuple12 >= tuple11)  # 输出: True，因为非空元组被认为比任何空元组大或相等
