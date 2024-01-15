import threading


# 定义一个函数，接收参数并进行处理
def process_data(data):
    # 在这里添加具体的数据处理逻辑
    result = data * 2
    print(f"Processed data: {result}")


if __name__ == "__main__":
    # 定义需要处理的参数列表
    data_list = [1, 2, 3, 4, 5]

    # 创建一个列表来存放线程对象
    threads = []

    # 创建线程并启动
    for data in data_list:
        thread = threading.Thread(target=process_data, args=(data,))
        threads.append(thread)
        thread.start()

    # 等待所有线程执行完毕
    for thread in threads:
        thread.join()
