import shutil


def copy_file(source_file, destination_file):
    shutil.copyfile(source_file, destination_file)
    print(f"拷贝文件：{source_file} -> {destination_file}")


def copy_directory(source_dir, destination_dir):
    shutil.copytree(source_dir, destination_dir)
    print(f"拷贝目录：{source_dir} -> {destination_dir}")


if __name__ == "__main__":
    source_path = "jpvm.sh"  # 源文件或目录路径
    destination_path = "arthas-output/"  # 目标文件或目录路径

    copy_file(source_path, destination_path)
