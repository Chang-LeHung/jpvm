def fib():
    return fib()


if __name__ == '__main__':
    try:
        fib()
    except RecursionError:
        print("RecursionError occurred")
