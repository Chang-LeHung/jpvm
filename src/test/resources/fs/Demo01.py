import os


def get_all_files_in_directory(directory):
    all_files = []

    # 遍历当前目录
    for entry in os.listdir(directory):
        full_path = os.path.join(directory, entry)

        # 判断是否为文件
        if os.path.isfile(full_path):
            all_files.append(full_path)
        # 判断是否为目录，如果是则递归调用函数
        elif os.path.isdir(full_path):
            all_files.extend(get_all_files_in_directory(full_path))

    return all_files


if __name__ == "__main__":
    current_directory = "."  # 当前目录
    all_files_in_current_directory = get_all_files_in_directory(current_directory)

    # for file_path in all_files_in_current_directory:
    #     print(file_path)
    print(f"size of files is {len(all_files_in_current_directory)}")
