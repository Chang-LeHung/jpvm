# 创建一个列表
my_list = [1, 2, 3, 4, 5]

# 使用列表方法进行测试

# # 添加元素
# my_list.append(6)
# print(my_list)  # 输出: [1, 2, 3, 4, 5, 6]
#
# # 在指定位置插入元素
# my_list.insert(2, 7)
# print(my_list)  # 输出: [1, 2, 7, 3, 4, 5, 6]
#
# # 删除指定位置的元素
# removed_element = my_list.pop(3)
# print(my_list)  # 输出: [1, 2, 7, 4, 5, 6]
# print(removed_element)  # 输出: 3
#
# # 删除指定值的元素
# my_list.remove(2)
# print(my_list)  # 输出: [1, 7, 4, 5, 6]
#
# # 切片操作
# sliced_list = my_list[1:4]
# print(sliced_list)  # 输出: [7, 4, 5]
#
# # 修改指定位置的元素
# my_list[0] = 10
# print(my_list)  # 输出: [10, 7, 4, 5, 6]
#
# # 获取列表的长度
# length = len(my_list)
# print(length)  # 输出: 5

# 判断元素是否存在于列表中
print(4 in my_list)  # 输出: True
print(8 in my_list)  # 输出: False

# # 元素排序
# my_list.sort()
# print(my_list)  # 输出: [4, 5, 6, 7, 10]
#
# # 列表反转
# my_list.reverse()
# print(my_list)  # 输出: [10, 7, 6, 5, 4]
#
# # 清空列表
# my_list.clear()
# print(my_list)  # 输出: []
#
# # 遍历列表元素
# for item in my_list:
#     print(item)  # 不会输出任何内容，因为列表为空
#
# my_list = [1, 2, 3, 4, 5]
#
# # 复制列表
# new_list = my_list.copy()
# print(new_list)  # 输出: [1, 2, 3, 4, 5]
#
# # 计数元素出现的次数
# count_of_3 = my_list.count(3)
# print(count_of_3)  # 输出: 1
#
# # 扩展列表
# extension = [6, 7, 8]
# my_list.extend(extension)
# print(my_list)  # 输出: [1, 2, 3, 4, 5, 6, 7, 8]
#
# # 获取元素的索引位置
# index_of_4 = my_list.index(4)
# print(index_of_4)  # 输出: 3
#
# # 移除指定位置的元素
# removed_element = my_list.pop(2)
# print(my_list)  # 输出: [1, 2, 4, 5, 6, 7, 8]
# print(removed_element)  # 输出: 3
#
# # 反向排序
# my_list.reverse()
# print(my_list)  # 输出: [8, 7, 6, 5, 4, 2, 1]
#
# # 删除列表中的所有元素
# my_list.clear()
# print(my_list)  # 输出: []
#
# # 使用列表生成器创建新列表
# new_list = [x for x in range(5)]
# print(new_list)  # 输出: [0, 1, 2, 3, 4]
#
# # 列表的切片赋值
# my_list = [1, 2, 3, 4, 5]
# my_list[1:3] = [6, 7, 8]
# print(my_list)  # 输出: [1, 6, 7, 8, 4, 5]
#
# # 列表的拼接
# list1 = [1, 2, 3]
# list2 = [4, 5, 6]
# concatenated_list = list1 + list2
# print(concatenated_list)  # 输出: [1, 2, 3, 4, 5, 6]
#
# # 使用列表方法删除指定值的所有元素
# my_list = [1, 2, 2, 3, 4, 2]
# value_to_remove = 2
# my_list = [x for x in my_list if x != value_to_remove]
# print(my_list)  # 输出: [1, 3, 4]