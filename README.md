# jpvm

# 项目介绍

使用 Java 语言重写 python 虚拟机，整个虚拟机的架构如下所示：

![](./docs/arch.png)

本项目重点实现虚拟机的部分，因此仍然使用 python 作为语言的编译器，可以使用编译之后的结果文件 .pyc 作为虚拟机的输入，本项目实现了对于
pyc 文件的解析与加载，并且将其封装成一个 `PyCodeObject` 对象。

# Demo

```python
def fib(n):
    if n == 0:
        return 0
    if n == 1:
        return 1
    return fib(n - 1) + fib(n - 2)


if __name__ == '__main__':
    print(fib(10))
```

使用如下命令对文件进行编译：

```bash
python -m compileall fib.py
```

执行上面的命令之后将会在当前目录下面有一个子目录  `__pycache__` 会存在一个文件 `fib.cpython-38.pyc` 。使用下面的 Java
程序执行这个 python 程序：

```java
import org.jpvm.errors.PyException;
import org.jpvm.pvm.PVM;

import java.io.IOException;

public class Example {

	public static void main(String[] args) {
		String filename = "src/test/resources/syntax/__pycache__/fib.cpython-38.pyc";
		try {
			new PVM(filename).run();
		} catch (PyException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
```

# Contribution
