try:
    a = 1 / 0
except ZeroDivisionError:
    print("ZeroDivisionError")
    raise
finally:
    print("finally")
