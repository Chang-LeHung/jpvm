
import atomic

val = atomic.AtomicLong(0)
print(val)

val.atomic_add(100)
print(val)
