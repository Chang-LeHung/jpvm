import sys
import dis

if __name__ == '__main__':
    with open(sys.argv[1], "r", encoding="utf-8") as fp:
        code = fp.read()
    dis.dis(code)
