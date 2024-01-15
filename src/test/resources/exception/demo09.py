try:
    a = 1 / 0
except Exception:
    try:
        a = 1 / 0
    except Exception:
        a = 1 / 0
finally:
    print("hello")
