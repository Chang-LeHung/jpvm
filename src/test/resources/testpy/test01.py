# 创建一个元组
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
print("*******")
# 元素的索引
print(my_tuple.index(3))  # 输出: 2
print("*******")
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